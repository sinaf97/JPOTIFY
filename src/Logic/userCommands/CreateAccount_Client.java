package Logic.userCommands;

import Logic.User;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * After that a user, completely entered his/her Information, you should call this class for
 * confirm his/her Information.
 */

public class CreateAccount_Client implements ServerInformation{

    private User user;
    private String userHostName;
    private int userPortNumber;

    public CreateAccount_Client(User user, String userHostName, int userPortNumber) {
        this.user = user;
        this.userHostName = userHostName;
        this.userPortNumber = userPortNumber;
    }

    /**
     *
     * @return Can you crate account by these Information or not
     * @throws IOException
     */


    public Boolean confirm() throws IOException, ClassNotFoundException {

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

        String order = "createAccount";
        out.writeObject(order);

        try {
            out.writeObject(this.user);
        }catch (Exception e){
            System.out.println(e);
        }

        String finalCommand = (String) in.readObject();

        if (finalCommand.equals("True")) {
            out.writeObject(this.userHostName);
            out.writeObject(this.userPortNumber);
            return true;
        } else {
            return false;
        }


    } // end main method

} // end class


