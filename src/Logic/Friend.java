package Logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Friend implements Serializable {
    private User user;
    private HashMap<String,User> friendsList; //String: userName, User


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

        for (User u:friendsList.values())
            if(u.getOnline())
                result.add(u);

        return result;
    }

    public HashMap<String, User> getFriendsList() {
        return friendsList;
    }
}
