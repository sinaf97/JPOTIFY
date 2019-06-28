package Logic.userCommands;

import Logic.SongSerial;
import Logic.User;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Download_Client implements ServerInformation{
    private User user;

    public Download_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @param file where do you like to download file in it
     * @param userNameOfYourFriend the userName of your friend that you want to download his/her file from his/her
     *                             sharePlayList
     * @param songName the name of that song you want to download it (with postFix)
     *
     * @throws IOException
     */
    public void download(File file, String userNameOfYourFriend , String songName) throws IOException {

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

        String order = this.user.getUsername() + "&Download&" + userNameOfYourFriend + "_" + songName;
        out.writeObject(order);

        FileOutputStream inMyComputer = null;

        try {
            try {
                inMyComputer = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }

            SongSerial songFile = (SongSerial) in.readObject();

            byte[] FileInByte = songFile.getFileInByte();

            inMyComputer.write(FileInByte);
            inMyComputer.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end main method

} // end class
