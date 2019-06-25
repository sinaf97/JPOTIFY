package server;

import Logic.Friend;
import Logic.User;

import java.util.ArrayList;

public class RequestManager {

    private String[] commands;
    private String returnCommand;
    private SocketServer socketServer;
    private User user;

    public RequestManager(String command,User user, SocketServer socketServer) {
        this.commands = command.split("&");
        this.socketServer = socketServer;
        this.user = user;
    }

    private void decodeCommand(String command) {
        if (this.commands.length == 1) {
            this.returnCommand = readCommand_1st(this.commands[0]);
        } else if (this.commands.length == 2) {
            this.returnCommand = readCommand_2nd(this.commands[0], this.commands[1]);
        }
    }

    private String readCommand_1st(String command) {
        String s = command + "&";
        switch (command) {
            case "getOnlineFriends": {
                for (String userName:user.getFriends().getFriendsList().keySet()) {
                    User  user= socketServer.getUsers().get(userName);
                    if (user.getOnline()) {
                        s = s + "&" + user.getName();
                    }
                }
                return s;
            }
            case "close": {
                for (String userName : user.getFriends().getFriendsList().keySet()) {
                    User user = socketServer.getUsers().get(userName);
                    if (user.getOnline()) {
                        s = s + "&" + user.getName();
                    }
                }
                return s;
            }
            case "singUp": {
                //get information from user
            }
            case "login": {

            }
            case "logout": {

            }
            case "close": {

            }
        }
        return s;
    }

    private String readCommand_2nd(String command1, String command2) {
        String s = command1 + "&" + command2;
        switch (command1) {
            case "Download": {

            }
            case "SharedList":
            {
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

    public String getReturnCommand() {
        return returnCommand;
    }
}
