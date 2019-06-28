package Logic.userCommands;

import Logic.User;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SharedPlaylist_GetSongsName_Client implements ServerInformation {

    private User user;

    public SharedPlaylist_GetSongsName_Client(User user) {
        this.user = user;
    }

    /**
     * <p>when a user call this class, the server search in his/her friends sharedPlayList. </p>
     *
     * @param userName your friend's userName, who you want to see his/her songsName in
     *                 his/her sharedPlayList.
     * @return just return a array "songsName" of his/her user friends who you want.
     * @throws IOException
     */
    public ArrayList <String> getSongsNameAction(String userName) throws IOException, ClassNotFoundException {

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

        String order = this.user.getUsername() + "&sharedPlaylist&" + "getSongsName&" + userName;
        out.writeObject(order);

        ArrayList <String> songsNameOfOneFriends = new ArrayList<>();
        String name;
        while (!(name = (String)in.readObject()).equals("-1")) {
            songsNameOfOneFriends.add(name);
        }

        return songsNameOfOneFriends;

    } // end main method

} // end class

