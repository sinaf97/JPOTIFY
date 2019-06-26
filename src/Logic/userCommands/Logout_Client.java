package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class Logout_Client {

    private User user;
    public static final String hostName = "127.0.0.1";
    public static final int portNumber = 44444;

    public Logout_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @return a array name of user's online friends, to say them this user get offline
     * @throws IOException
     */
    public String[] logoutAction() throws IOException {

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

        String order = this.user.getUsername() + "&logout";
        out.println(order);

        String commandFriends = in.readLine();

        return commandFriends.split("&");


    } // end main method

} // end class

