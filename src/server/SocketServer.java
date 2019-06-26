package server;

import Logic.Media;
import Logic.MediaList;
import Logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer {
    private final int portNumber = 44444;
    private ServerSocket serverSocket = null;
    private HashMap <String, User> users = new HashMap<>(); //String: userName, User
    private HashMap <String, MediaList> sharedPlaylist = new HashMap<>(); //String: userName, sharedPlaylist
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
            MediaList mediaList = new MediaList(user.getUsername());
            this.sharedPlaylist.put(user.getUsername(), mediaList);
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

    public void addTosharedPlaylist(String username, Media media) {
        if (sharedPlaylist.containsKey(username))
        this.sharedPlaylist.get(username).addSong(media);
    }

    public void removeFromsharedPlaylist(String userName, Media media) {
        if (sharedPlaylist.containsKey(userName)) {
            this.sharedPlaylist.get(userName).removeSong(media);
        }
    }

    public HashMap <String, MediaList> getSharedPlaylist() {
        return this.sharedPlaylist;
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


