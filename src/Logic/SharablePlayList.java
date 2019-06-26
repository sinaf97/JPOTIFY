package Logic;

import java.io.Serializable;

/**
 * this class has a cloud on server system that can store the user Shared list
 * in the server system.
 */
public class SharablePlayList extends CustomPlayList implements Serializable {

    public SharablePlayList(){
        super("Sharable PlaylistUI");
    }

    @Override
    public void setName(String name) { }

    /*
    server codes
     */
}
