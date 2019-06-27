package Logic.userCommands;

import Logic.SongSerial;
import Logic.User;

import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class RemoveFromSharedPlaylist_Client implements ServerInformation{

    private User user;

    public RemoveFromSharedPlaylist_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @param songName the name of song that user want to delete it
     * @return (if process be successfully: return true ) vs (else: return false)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean removeFromSharedPlaylist(String songName) throws IOException, ClassNotFoundException {

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

        String order = this.user.getUsername() + "&removeFromSharedPlaylist&"  + songName;
        out.writeObject(order);

        return ((String)in.readObject()).equals("File deleted successfully");

    } // end main method

} // end class

