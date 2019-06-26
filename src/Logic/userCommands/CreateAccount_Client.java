package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

/**
 * After that a user, completely entered his/her Information, you should call this class for
 * confirm his/her Information.
 */

public class CreateAccount_Client implements ServerInformation{

    private User user;

    public CreateAccount_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @return Can you crate account by these Information or not
     * @throws IOException
     */

    /*
    public Boolean confirm() throws IOException {

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

        String order = this.user.getUsername() + "&createAccount";
        out.println(order);

        String commandFriends = in.readLine();

        return commandFriends.split("&");


    } // end main method
    */

} // end class


