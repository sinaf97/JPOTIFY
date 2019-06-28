package Logic.userCommands;

import Logic.User;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class Close_Client implements ServerInformation {

    private User user;

    public Close_Client(User user) {
        this.user = user;
    }

    /**
     * <p>
     *     "close" and "logout" classes are just the same.
     * </p>
     *
     * @throws IOException
     */
    public void closeAction() throws IOException {

        Socket clientSocket = null;
        ObjectOutputStream out = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = this.user.getUsername() + "&close";
        out.writeObject(order);

        out.writeObject(this.user);


    } // end main method

} // end class

