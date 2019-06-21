package Logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Friend {
    private User user;
    private HashMap<String,User> friendsList;


    public Friend(User user){
        this.user = user;
        friendsList = new HashMap<>();
    }


    public void addFriend(User newFriend){
        if(!friendsList.containsKey(newFriend.getUsername()))
            friendsList.put(newFriend.getUsername(),newFriend);
    }
    public void removeFriend(User exFriend){
        if(friendsList.containsKey(exFriend.getUsername()))
            friendsList.remove(exFriend.getUsername(),exFriend);
    }
    public ArrayList<User> getOnlineFriends(){
        ArrayList<User> result = new ArrayList<>();
        /*
        send request to server for information
         */

        return result;
    }


}
