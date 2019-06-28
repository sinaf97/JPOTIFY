package Logic.friendsActivity;

import Logic.User;
import server.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FriendManagerRunnable implements Runnable{
    private SocketServer socketServer;
    private User user;

    protected Socket clientSocket = null;

    public FriendManagerRunnable(Socket clientSocket,User user) {
        this.user = user;

        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            ObjectInputStream in = new ObjectInputStream((this.clientSocket.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            String command = (String) in.readObject();

            manageCommand(command, in, out);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void manageCommand(String command, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        switch (command) {
            case "offline": {
                User friend = (User) in.readObject();
                user.getFriends().getFriendsList().put(friend.getUsername(),friend);
                user.getUi().getRight().updateFriends();
            }
            case "online": {
                User friend = (User) in.readObject();
                user.getFriends().getFriendsList().put(friend.getUsername(),friend);
                user.getUi().getRight().updateFriends();
            }
            case "play": {
                User friend = (User) in.readObject();
                /*
                            sina farahini
                 */
            }
            case "pause": {
                User friend = (User) in.readObject();
                /*
                            sina farahini
                 */
            }
        }

    }


}
