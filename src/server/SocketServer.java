package server;

import Logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
    private final int portNumber = 44444;
    private ServerSocket serverSocket = null;
    private ArrayList <User> users = null;

    public void runServer() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            try {
                Socket clintSocket = this.serverSocket.accept();
                JpotifyRunnable a = new JpotifyRunnable(clintSocket);
                new Thread(a).start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void addUser(User user) {
        if(!this.users.contains(user))  {
            this.users.add(user);
        }
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}


