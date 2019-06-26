package Logic.friendsActivity;

public class FriendServer {
    public static void main(String[] args){
        SocketServer_Friend s = new SocketServer_Friend();
        s.runServer();
    }
}
