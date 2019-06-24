package server;

public class JpotifyServer {
    public static void main(String[] args) {
        System.out.println("Hello Server...");
        SocketServer s = new SocketServer();
        s.runServer();
    }
}
