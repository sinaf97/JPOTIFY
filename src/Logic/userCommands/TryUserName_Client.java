package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class TryUserName_Client implements ServerInformation {

    private User user;

    public TryUserName_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @return a array name of user's online friends, to say them this user get offline
     * @throws IOException
     */
    public void LogoutAction() throws IOException {

        Socket clientSocket = null;
        ObjectOutputStream out = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        String order = this.user.getUsername() + "&logout";
        out.writeObject(order);

        out.writeObject(this.user);

//
//        return commandFriends.split("&");


    } // end main method

} // end class

