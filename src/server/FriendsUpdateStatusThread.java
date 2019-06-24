package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * change inputStream code to GIU code
 */
public class FriendsUpdateStatusThread extends Thread{

    private Socket clientSocket = null;

    public FriendsUpdateStatusThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        /*
        //something like this:

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String arg1 = in.readLine();
            System.out.println("Client Says: " + arg1);
            out.println("Thanks for the massage!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

}
