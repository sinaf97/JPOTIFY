package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class Play_Client implements ServerInformation{

    private User user;

    public Play_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @return a array name of user's online friends, to say them this user get offline
     * @throws IOException
     */
    public void playAction(String time, String songName) throws IOException {

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

        String order = this.user.getUsername() + "&play&" + songName + "&" + time;
        out.writeObject(order);

        String nothing = in.readLine();

//        return commandFriends.split("&");


    } // end main method

} // end class
