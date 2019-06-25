package Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HandleSharableListThread_Client {
    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 44444;
        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        InputStreamReader ir;
        BufferedReader stdIn;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            ir = new InputStreamReader(clientSocket.getInputStream());
            in = new BufferedReader(ir);
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            out.println("initCalculation");

            String finalCommand = in.readLine();

        } catch (UnknownHostException e) {
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        } //end try-catch

    } // end main method

} // end class
