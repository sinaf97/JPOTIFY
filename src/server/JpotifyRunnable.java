package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// we prefer to implement runnable interface rather than extend thread class
public class JpotifyRunnable implements Runnable{

    protected Socket clientSocket = null;

    public JpotifyRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String arg1 = in.readLine();
            System.out.println("Client Says: " + arg1);
            out.println("Thanks for the massage!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











