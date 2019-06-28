package Logic.friendsActivity;

import Logic.Media;
import Logic.MediaList;
import Logic.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer_Friend {
    private final int portNumber = 44444;
    private ServerSocket serverSocket = null;
    private User user;

    public SocketServer_Friend(User user){
        this.user = user;
    }


    public void runServer() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            try {
                Socket clintSocket = this.serverSocket.accept();
                FriendManagerRunnable a = new FriendManagerRunnable(clintSocket,user);
                new Thread(a).start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}


