package Logic.userCommands;

import Logic.SongSerial;
import Logic.User;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Upload_Client implements ServerInformation{

    private User user;

    public Upload_Client(User user) {
        this.user = user;
    }

    /**
     * in fact, by this class, a user add a song to his/her sharedPlayList
     *
     * @param file the path of the media in the user computer
     * @throws IOException
     */
    public Boolean upload(File file) throws IOException, ClassNotFoundException {

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

        String order = this.user.getUsername() + "&Upload&"  + file.getName();
        out.writeObject(order);

        SongSerial songFile = new SongSerial(file);

        out.writeObject(songFile);

        return ((String)in.readObject()).equals("Process done successfully");

    } // end main method

} // end class

