package server;

import Logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer {
    private final int portNumber = 44444;
    private ServerSocket serverSocket = null;
    private HashMap <String, User> users = null; //String: userName, User

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
        }
    }

    public void removeUser(User user) {
        this.users.remove(user.getUsername(), user);
    }

    public HashMap<String, User> getUsers() {
        return this.users;
    }
}


