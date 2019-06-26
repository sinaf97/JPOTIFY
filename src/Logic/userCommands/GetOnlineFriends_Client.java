package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

/**
 * This class has 3 usages: <p>
 * 1) when a user "open" the Jpotify panel, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 * <p>2) when a user "sign up" into Jpotify, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 * <p>3) when a user "login" into Jpotify, should get him/her online friends
 *          and him/her online friends should understand that this user get online. (two works) </p>
 */

public class GetOnlineFriends_Client {

    private User user;
    public static final String hostName = "127.0.0.1";
    public static final int portNumber = 44444;

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
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = this.user.getUsername() + "&getOnlineFriends";
        out.println(order);

        String commandFriends = in.readLine();

        return commandFriends.split("&");


    } // end main method

} // end class

