package Logic.userCommands;

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

public class Upload_Client implements ServerInformation{

    private User user;

    public Upload_Client(User user) {
        this.user = user;
    }

    /**
     *
     * @param file the path of the media in the user computer
     * @param songName the unique name of that song
     * @throws IOException
     */
    public Boolean upload(File file, String songName) throws IOException {

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

        String order = this.user.getUsername() + "&Upload&"  + songName;
        out.writeObject(order);

        FileInputStream inMyComputer = null;

        try {
            try {
                inMyComputer = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }

            int c;
            String music = "";
            while ((c = inMyComputer.read()) != -1) {
                music += (char)c;
            }
            out.writeObject(music);


        } finally {
            if (inMyComputer != null) {
                inMyComputer.close();
            }
        }

        return in.readLine().equals("Process done successfully");

    } // end main method

} // end class

