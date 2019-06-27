package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class RemoveFriend_Client implements ServerInformation {

    private User yourUser;
    private User yourFriendUser;

    public RemoveFriend_Client(User yourUser, User yourFriendUser) {
        this.yourUser = yourUser;
        this.yourFriendUser = yourFriendUser;
    }

    /**
     * at first, user call this class to get allowance.
     *
     * @return <p>if yourFriendUser be exist, return "true" and then, "you
     *          should remove this user from your friendsList and remove yourself from his/her friendsList".</p>
     *          <p>else, this function return null</p>
     * @throws IOException
     */
    public Boolean breakUp() throws IOException, ClassNotFoundException {

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

        String order = this.yourUser.getUsername() + "&removeFriend";
        out.writeObject(order);

        out.writeObject(this.yourFriendUser);

        return ((String) in.readObject()).equals("True");

    } // end main method

} // end class

