package Logic.userCommands;

import Logic.User;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;

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
     * @param songName the name of that song you want to download it
     *
     * @throws IOException
     */
    public void download(File file, String userNameOfYourFriend , String songName) throws IOException {

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

        String order = this.user.getUsername() + "&Download&" + userNameOfYourFriend + "_" + songName;
        out.writeObject(order);

        FileOutputStream inMyComputer = null;

        try {
            try {
                inMyComputer = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }

            int c;
            while ((c = in.read()) != '\0') {
                inMyComputer.write((char)c);
            }

        } finally {
            if (inMyComputer != null) {
                inMyComputer.close();
            }
        }


    } // end main method

} // end class