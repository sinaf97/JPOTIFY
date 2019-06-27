package server;

import Logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer {
    private final int portNumber = 44444;
    private ServerSocket serverSocket = null;
    private HashMap <String, User> users = new HashMap<>(); //String: userName, User
    private HashMap <String, UserFolder> sharedPlaylist = new HashMap<>(); //String: userName, sharedPlaylist
    private HashMap <User, String> userHostNames = new HashMap<>();
    private HashMap <User, Integer> userPortNumbers = new HashMap<>();

    public void runServer() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            try {
                Socket clintSocket = this.serverSocket.accept();
                JpotifyManagerRunnable a = new JpotifyManagerRunnable(clintSocket, this);
                new Thread(a).start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void addUser(User user) {
        if(!this.users.containsKey(user.getUsername()))  {
            this.users.put(user.getUsername(), user);
            UserFolder userFolder = new UserFolder(user.getUsername());
            this.sharedPlaylist.put(user.getUsername(), userFolder);
        }
    }

    public void removeUser(User user) {
        if (this.users.containsKey(user.getUsername())) {
            this.users.remove(user.getUsername(), user);
            this.sharedPlaylist.remove(user.getUsername());
        }
    }

    public HashMap <String, User> getUsers() {
        return this.users;
    }

    public void updateUser(User user) {
        if(this.users.containsKey(user.getUsername()))  {
            this.users.put(user.getUsername(), user);
        }
    }


    public void addToSharedPlaylist(String username, String songName) {
        if (sharedPlaylist.containsKey(username))
        this.sharedPlaylist.get(username).addSongName(songName);
    }

    public void removeFromSharedPlaylist(String userName, String songName) {
        if (sharedPlaylist.containsKey(userName)) {
            this.sharedPlaylist.get(userName).removeSongName(songName);
        }
    }

    public HashMap<String, UserFolder> getSharedPlaylist() {
        return sharedPlaylist;
    }

    public void addToUserHostNames(User user, String hostName) {
        this.userHostNames.put(user, hostName);
    }

    public void addToUserPortNumbers(User user, int portNumber) {
        this.userPortNumbers.put(user, portNumber);
    }

    public HashMap <User, String> getUserHostNames() {
        return userHostNames;
    }

    public HashMap <User, Integer> getUserPortNumbers() {
        return userPortNumbers;
    }
}


