package server;

import Logic.User;

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
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String command = in.readLine();
            manageCommand(command, in, out);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manageCommand(String command, BufferedReader in, PrintWriter out) {
        this.commands = command.split("&");
        if ((findUser(this.commands[0]) != null) && (this.commands.length > 1)) {
            if (this.commands.length == 2) {
                this.returnCommand = readCommand_1st(this.commands[1]);
            } else if (this.commands.length == 3) {
                this.returnCommand = readCommand_2nd(this.commands[1], this.commands[2], in, out);
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
        return null;
    }

    private String readCommand_1st(String command) {
        String s;
        switch (command) {
            case "getOnlineFriends": {
                s = "onlineFriends&";
                return addFriends(s);
            }
            case "close": {
                s = command + "&";
                return addFriends(s);
            }
            case "open": {
                s = command + "&";
                return addFriends(s);
            }
            case "singUp": {
                s = command + "&";
                return addFriends(s);
            }
            case "login": {
                s = command + "&";
                return addFriends(s);
            }
            case "logout": {
                s = command + "&";
                return addFriends(s);
            }
        }
        return null;
    }

    private String readCommand_2nd(String command1, String command2, BufferedReader in, PrintWriter out) {
        String s = null;
        switch (command1) {
            case "Download": {                  //userName(who order)&Download&userName(who wanted SharedList)
                s = downloadManager();
                User user2 = findUser(command2);
                return s;
            }
            case "Upload": {
                User user2 = findUser(command2);
                if(uploadManager(in)) {
                    return "Process done successfully.";
                } else {
                    return "Process is not successfully";
                }

            }
            case "SharedList": {
                switch (command2) {
                    case "getList":
                    {
                        //return the lists
                        return s;
                    }
                    case "getSongs":
                    {
                        //return the songs
                        return s;
                    }
                }
            }
        }
        return s;
    }

    private String downloadManager() {

        File file = new File("Mohammad.txt");
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

    private Boolean uploadManager(BufferedReader in) {
        File file = new File("Mohammad(socket).txt");
        FileOutputStream inMyComputer = null;

        try {
            try {
                inMyComputer = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//                out.write(-1);
            int c;
            try {
                while ((c = in.read()) != '\0') {
                    inMyComputer.write((char) c);
                    return true;
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
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
    
}










