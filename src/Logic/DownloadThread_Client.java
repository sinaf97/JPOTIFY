package Logic;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileNotFoundException;

public class DownloadThread_Client {
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

        out.println("please download Ali");

        File file = new File("Mohammad(client).txt");
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
