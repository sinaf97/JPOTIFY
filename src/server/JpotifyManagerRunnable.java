package server;

import Logic.Media;
import Logic.MediaList;
import Logic.Status;
import Logic.User;

import javax.jws.soap.SOAPBinding;
import java.io.ObjectOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;


// we prefer to implement runnable interface rather than extend thread class
public class JpotifyManagerRunnable implements Runnable{

    private String[] commands;      //the first element of  this array is the username of the user who has order
    private SocketServer socketServer;
    private User user;

    protected Socket clientSocket = null;

    public JpotifyManagerRunnable(Socket clientSocket, SocketServer socketServer) {
        this.clientSocket = clientSocket;
        this.socketServer = socketServer;
    }


    /**
     * command like: mohammad(userName&()...)
     */
    @Override
    public void run() {

        try {
            ObjectInputStream in = new ObjectInputStream((this.clientSocket.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            String command =(String) in.readObject();

            if (command.equals("createAccount")) {
                String s = null;
                User user = (User) in.readObject();
                if (!this.socketServer.getUsers().containsKey(user.getUsername())) {
                    this.socketServer.addUser(user);
                    s = "True";
                    out.writeObject(s);
                } else {
                    s = "False";
                    out.writeObject("False");
                }

                if (s.equals("True")) {
                    String userHostName = (String) in.readObject();
                    this.socketServer.addToUserHostNames(user, userHostName);

                    Integer userPortNumber = (Integer) in.readObject();
                    this.socketServer.addToUserPortNumbers(user, userPortNumber);
                }

            } else {

                manageCommand(command, in, out);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void manageCommand(String command, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        this.commands = command.split("&");
        if ((findUser(this.commands[0]) != null) && (this.commands.length > 1)) {
            if (this.commands.length == 2) {
                readCommand_1st(this.commands[1], in, out);
            } else if (this.commands.length == 3) {
                readCommand_2nd(this.commands[1], this.commands[2], in, out);
            } else if (this.commands.length == 4) {
                readCommand_3rd(this.commands[1], this.commands[2], this.commands[3]);
            }
        }
    }

    private User findUser(String command) {
        for (User u:socketServer.getUsers().values()) {
            if (u.getUsername().equals(command)) {
                this.user = u;
                return u;
            }
        }
        return null;
    }

    /**
     * for complete the command that we want to sent to client
     *
     * @return
     */
    private ArrayList <User> addFriends() {
        ArrayList <User> friends = new ArrayList<>();
        for (User user:this.user.getFriends().getFriendsList().values()) {
            if (user.getOnline()) {
                friends.add(user);
            }
        }
        return friends;
    }

    private void readCommand_1st(String command ,ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String s = "";
        switch (command) {
            case "getMyself": {
                this.user.setOnline(true);
                out.writeObject(this.user);
                ArrayList <User> friends = addFriends();
                MyThread thread = new MyThread("Thread", friends, "online");
                thread.start();
            }

            case "close": {
                User user = (User) in.readObject();
                this.socketServer.updateUser(user);
                this.user = user;
                ArrayList <User> friends = addFriends();
                MyThread thread = new MyThread("Thread", friends, "offline");
                thread.start();
            }
            case "play":
            case "pause": {
                User user = (User) in.readObject();
                this.socketServer.updateUser(user);
                this.user = user;
                ArrayList <User> friends = addFriends();
                MyThread thread = new MyThread("Thread", friends, command);
                thread.start();
            }
//            case "open": {
//                return addFriends(s);
//            }
//            case "singUp": {
//                return addFriends(s);
//            }
//            case "login": {
//                return addFriends(s);
//            }

        }
    }

    private String readCommand_2nd(String command1, String command2, ObjectInputStream in, ObjectOutputStream out) {
        String s = "";
        switch (command1) {
            case "Download": {                  //userName(who order)&Download&userName(who wanted SharedList)
                String[] userName_songName = command2.split("_");
                User user2 = findUser(userName_songName[0]);
                s = downloadManager(user2.getUsername(), userName_songName[1]);
                return s;
            }
            case "Upload": {                    //command2: songName
                String songName = command2;
                if(uploadManager(in, songName)) {
                    return "Process done successfully";
                } else {
                    return "Process Did Not complete successfully";
                }

            }
            case "sharedPlaylist": {            //command2 : getList
                s = command1 + "&";
                for (String userName : this.user.getFriends().getFriendsList().keySet()) {
                    if (this.socketServer.getSharedPlaylist().containsKey(userName)) {
                        s = s + socketServer.getSharedPlaylist().get(userName).getName() + "&";
                    }
                }
                return s;
                    }
        }
        return s;
    }

    private String readCommand_3rd(String command1, String command2, String command3) {
        String s = null;
        switch (command1) {
            case "sharedPlaylist": {
                switch (command2) {
                    case "getSongs": { //command3: the user of who we want to see his sharedPlaylist
                        s = command1 + "&" + "songs" + "&";
                        User user2 = findUser(command3);
                        if ( user2 != null) {
                            for (Media media : this.socketServer.getSharedPlaylist().get(user2.getUsername()).getSongs()) {
                                s += media.getName();
                            }
                        }
                    }
                }
            }

        }

        return s;
    }


    /**
     *
     * @param userName the name of the SharedList in the memory of server computer; is a folder
     * @param songName the name of the song (has the prefix of media)
     * @return link for download
     */
    private String downloadManager(String userName, String songName) {

        File file = new File(userName + "\\" + songName);
        FileInputStream inMyComputer = null;
        String music = "";

        try {
            try {
                inMyComputer = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int c;
            while ((c = inMyComputer.read()) != -1) {
                music += (char)c;
            }

        } catch (IOException io) {
            System.out.println(io);
        } finally {
            if (inMyComputer != null) {
                try {
                    inMyComputer.close();
                } catch (IOException io) {
                    System.out.println(io);
                    return music;
                }
            }
        }

        return null;

    }

    /**
     *
     * @param in the BufferedReader of user
     * @param songName the name of the song (has the prefix of media)
     * @return
     */
    private Boolean uploadManager(ObjectInputStream in, String songName) {

        //the name of the sharedList folder
        File file = new File(this.user.getUsername() + "\\" + songName);
        FileOutputStream inMyComputer = null;

        try {
            try {
                inMyComputer = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//                out.write(-1);
            String music = (String) in.readObject();
            try {
                for(int i = 0; i < music.length(); i++) {
                    inMyComputer.write(music.charAt(i));
                }
                return true;

            } catch (IOException io) {
                io.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inMyComputer != null) {
                    inMyComputer.close();
                }
            }
            catch (IOException io) {
                io.printStackTrace();
            }
        }
        return false;
    }

    /**
     * userSocket should handle:
     * <p> 1) offline </p>
     * <p> 2) online </p>
     * <p> 3) offline </p>
     *
     */
    private void notifyFriends(ArrayList <User> friends, String status) throws IOException {

        for (User u:friends) {
            String userHostNames = this.socketServer.getUserHostNames().get(u);
            Integer userPortNumbers = this.socketServer.getUserPortNumbers().get(u);

            Socket clientSocket = null;
            ObjectOutputStream out = null;

            try {
                clientSocket = new Socket(userHostNames, userPortNumbers);
                // create our IO streams
                out = new ObjectOutputStream(clientSocket.getOutputStream());

            } catch (IOException e) {
                System.exit(1);
            } //end try-catch

            out.writeObject(status);
            switch (status) {
                case "offline": {
                    out.writeObject(this.user);
                    break;
                }
                case  "online": {
                    out.writeObject(this.user);
                    break;
                }
                case "play":
                case "pause": {
                    out.writeObject(this.user);
                    break;
                }

            }

        }

    }

    private class MyThread extends Thread {
        private ArrayList <User> friends;
        private String status;

        public MyThread(String name, ArrayList <User> friends, String status) {
            super(name);
            this.friends = friends;
            this.status = status;
        }

        @Override
        public void run() {
            try {
                notifyFriends(this.friends, this.status);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}










