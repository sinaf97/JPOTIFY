package server;

public class JpotifyServer {
    public static void main(String[] args) {
        SocketServer s = new SocketServer();
        s.runServer();
    }
}
