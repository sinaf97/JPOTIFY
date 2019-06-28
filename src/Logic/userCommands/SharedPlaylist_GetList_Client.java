package Logic.userCommands;

import Logic.User;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SharedPlaylist_GetList_Client implements ServerInformation {

    private User user;

    public SharedPlaylist_GetList_Client(User user) {
        this.user = user;
    }

    /**
     * <p>when a user call this class, the server search in his/her friends. </p>
     *
     * @return just return a array "userName" of his/her user friends who has song in sharableList
     * @throws IOException
     */
    public ArrayList <String> getListAction() throws IOException, ClassNotFoundException {

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

        String order = this.user.getUsername() + "&sharedPlaylist&" + "getList";
        out.writeObject(order);

        ArrayList <String> userNameOfFriends = new ArrayList<>();
        String name;
        while (!(name = (String)in.readObject()).equals("-1")) {
            userNameOfFriends.add(name);
        }

        return userNameOfFriends;

    } // end main method

} // end class

