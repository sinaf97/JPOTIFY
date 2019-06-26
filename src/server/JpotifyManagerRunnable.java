package server;

import Logic.Media;
import Logic.MediaList;
import Logic.Status;
import Logic.User;
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


// we prefer to implement runnable interface rather than extend thread class
public class JpotifyManagerRunnable implements Runnable{

    private String[] commands;      //the first element of  this array is the username of the user who has order
    private String returnCommand;
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
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String command =(String) in.readObject();

            if (command.equals("createAccount")) {
                String s = null;
                User user = (User) in.readObject();
                if (!this.socketServer.getUsers().containsKey(user.getUsername())) {
                    this.socketServer.addUser(user);
                    s = "True";
                    out.println(s);
                } else {
                    s = "False";
                    out.println("False");
                }

                if (s.equals("True")) {
                    String userHostName = (String) in.readObject();
                    this.socketServer.addToUserHostNames(user, userHostName);

                    Integer userPortNumber = (Integer) in.readObject();
                    this.socketServer.addToUserPortNumbers(user, userPortNumber);
                }

            } else {

                manageCommand(command, in, out);
                if (this.returnCommand != null) {
                    out.println(this.returnCommand);
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void manageCommand(String command, ObjectInputStream in, PrintWriter out) {
        this.commands = command.split("&");
        if ((findUser(this.commands[0]) != null) && (this.commands.length > 1)) {
            if (this.commands.length == 2) {
                this.returnCommand = readCommand_1st(this.commands[1]);
            } else if (this.commands.length == 3) {
                this.returnCommand = readCommand_2nd(this.commands[1], this.commands[2], in, out);
            } else if (this.commands.length == 4) {
                this.returnCommand = readCommand_3rd(this.commands[1], this.commands[2], this.commands[3]);
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
     * @param s
     * @return
     */
    private String addFriends(String s) {
        for (String userName:user.getFriends().getFriendsList().keySet()) {
            User user= socketServer.getUsers().get(userName);
            if (user.getOnline()) {
                s = s + "&" + user.getName();
            }
        }
        return s.substring(1);
    }

    private String readCommand_1st(String command) {
        String s = "";
        switch (command) {
            case "getOnlineFriends": {
                this.user.setOnline(true);
                String friends = addFriends(s);
                MyThread thread = new MyThread("Thread", friends, "online");
                thread.start();
                return friends;
            }

            case "close": {
                this.user.setOnline(false);
                String friends = addFriends(s);
                MyThread thread = new MyThread("Thread", friends, "offline");
                thread.start();
                return " ";
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
            case "logout": {
                this.user.setOnline(false);
                String friends = addFriends(s);
                MyThread thread = new MyThread("Thread", friends, "offline");
                thread.start();
                return " ";
            }

        }
        return null;
    }

    private String readCommand_2nd(String command1, String command2, ObjectInputStream in, PrintWriter out) {
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
            case "play": {                     //command2: songName, command3: time

            }
            case "pause": {
                String friends = addFriends(s);
                MyThread thread = new MyThread("Thread", friends, command1, command2, command3);
                thread.start();
                return " ";

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
            music += (char)('\0');

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
    private void notifyFriends(String friends, String status, String moreInfo_1, String moreInfo_2) {
        String[] arrayFriend = friends.split("&");

        for (String f:arrayFriend) {
            User friend = findUser(f);
            String userHostNames = this.socketServer.getUserHostNames().get(friend);
            Integer userPortNumbers = this.socketServer.getUserPortNumbers().get(friend);

            Socket clientSocket = null;
            ObjectOutputStream out = null;

            try {
                clientSocket = new Socket(userHostNames, userPortNumbers);
                // create our IO streams
                out = new ObjectOutputStream(clientSocket.getOutputStream());

            } catch (IOException e) {
                System.exit(1);
            } //end try-catch

            String order = "";

            switch (status) {
                case "online":
                case "offline": {
                    order = this.user.getUsername() + "&" + status;
                    break;
                }
                case "play":
                case "pause": {
                    order = this.user.getUsername() + "&" + status + "&" + moreInfo_1 + "&" + moreInfo_2;
                    break;
                }

            }

            out.println(order);

        }



    }

    private class MyThread extends Thread {
        private String friends;
        private String status;
        private String moreInfo_1 = null;
        private String moreInfo_2 = null;

        public MyThread(String name, String friends, String status) {
            super(name);
            this.friends = friends;
            this.status = status;
        }

        public MyThread(String name, String friends, String status, String moreInfo_1, String moreInfo_2) {
            super(name);
            this.friends = friends;
            this.status = status;
            this.moreInfo_1 = moreInfo_1;
            this.moreInfo_2 = moreInfo_2;
        }


        @Override
        public void run() {
            notifyFriends(this.friends, this.status, this.moreInfo_1, this.moreInfo_2);
        }
    }
    
}










