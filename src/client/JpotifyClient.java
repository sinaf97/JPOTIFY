package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class JpotifyClient {

    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 44444;
        Socket clientSocket;
//        PrintWriter out;
//        BufferedReader in;
//        InputStreamReader ir;

        try {
            clientSocket = new Socket(hostName, portNumber);
            // create our IO streams
//            out = new PrintWriter(clientSocket.getOutputStream(), true);
//            ir = new InputStreamReader(clientSocket.getInputStream());
//            in = new BufferedReader(ir);
//            out.println("initCalculation");
//            System.out.println("Server says: " + in.readLine());
        } catch (UnknownHostException e) {
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        } //end try-catch
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end main method

} // end class


