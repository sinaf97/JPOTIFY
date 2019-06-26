package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;

/**
 * This class has 3 usages: <p>
 * 1) when a user "open" the Jpotify panel, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 * <p>2) when a user "sign up" into Jpotify, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 * <p>3) when a user "login" into Jpotify, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 *          
 * <p>Note: don't forget: before a user "sing up", should "create account". these commands
 *          are completely different... </p>
 */

public class GetOnlineFriends_Client implements ServerInformation{

    private User user;

    public GetOnlineFriends_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @return a array name of user's online friend
     * @throws IOException
     */
    public String[] getOnlineFriends() throws IOException {

        Socket clientSocket = null;
        ObjectOutputStream out = null;
        BufferedReader in = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = this.user.getUsername() + "&getOnlineFriends";
        out.writeObject(order);

        String commandFriends = in.readLine();

        return commandFriends.split("&");


    } // end main method

} // end class

