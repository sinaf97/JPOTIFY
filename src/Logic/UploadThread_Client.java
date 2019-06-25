package Logic;

import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileNotFoundException;

public class UploadThread_Client {
    public static void main(String[] args) throws IOException {
        String hostName = "127.0.0.1";
        int portNumber = 44444;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

        out.println("upload");

        File file = new File("Mohammad.txt");
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
            music += (char)('\0');
            out.println(music);

        } finally {
            if (inMyComputer != null) {
                inMyComputer.close();
            }
        }


    } // end main method

} // end class

