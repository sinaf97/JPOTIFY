package Logic.userCommands;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class TryUserName_Client implements ServerInformation {

    private String userName;

    public TryUserName_Client(String userName) {
        this.userName = userName;
    }

    /**
     * when a user want to logIn to Jpotify, he/she enter his/her userName and sent it to server.
     * server check it.
     *
     * <p> Warning: by this class, user doesn't get online...
     * after he/she get true from server, he/she can call "GetOnline" class. </p>
     *
     * @return (if server find that userName: return true), (else: return false)
     *
     * @throws IOException
     */
    public Boolean tryUserNameAction() throws IOException, ClassNotFoundException {

        Socket clientSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream((clientSocket.getInputStream()));

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = "tryUserName";
        out.writeObject(order);

        out.writeObject(this.userName);
        String temp = (String) in.readObject();
        return (temp.equals("True"));

    } // end main method

} // end class

