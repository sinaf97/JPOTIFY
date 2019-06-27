package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class AddFriend_Client implements ServerInformation {

    private User yourUser;
    private String yourFriendUserName;

    public AddFriend_Client(User yourUser, String yourFriendUserName) {
        this.yourUser = yourUser;
        this.yourFriendUserName = yourFriendUserName;
    }

    /**
     * at first, user call this class to get allowance.
     *
     * @return <p>if yourFriendUserName be exist, this function find that user and return it and "you
     *          should assign this user to your friends and assign yourself to his/her friends".</p>
     *          <p>else, this function return null</p>
     * @throws IOException
     */
    public User makeFriend() throws IOException, ClassNotFoundException {

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

        String order = this.yourUser.getUsername() + "&addFriend";
        out.writeObject(order);

        out.writeObject(this.yourFriendUserName);

        if (((String)in.readObject()).equals("True")) {
            return (User)in.readObject();
        } else {
            return null;
        }

    } // end main method

} // end class

