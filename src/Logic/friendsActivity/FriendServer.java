package Logic.friendsActivity;

import Logic.User;

public class FriendServer {


    public FriendServer(User user){
        runServer(user);
    }
    public void runServer(User user){
        SocketServer_Friend s = new SocketServer_Friend(user);
        s.runServer();
    }
}
