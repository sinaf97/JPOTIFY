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

    public FriendManagerRunnable(Socket clientSocket) {
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

    private void manageCommand(String command, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        switch (command) {
            case "offline": {
                this.user = (User) in.readObject();
                /*
                            sina farahini
                 */
                break;
            }
            case "online": {
                this.user = (User) in.readObject();
                /*
                            sina farahini
                 */
            }
            case "play": {
                this.user = (User) in.readObject();
                /*
                            sina farahini
                 */
            }
            case "pause": {
                this.user = (User) in.readObject();
                /*
                            sina farahini
                 */
            }
        }

    }


}
