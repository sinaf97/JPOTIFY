package Logic.userCommands;

import Logic.User;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class Pause_Client implements ServerInformation{

    private User user;

    public Pause_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @throws IOException
     */
    public void pauseAction() throws IOException, ClassNotFoundException {

        Socket clientSocket = null;
        ObjectOutputStream out = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = this.user.getUsername() + "&pause";
        out.writeObject(order);

        out.writeObject(this.user);
    }

} // end class
