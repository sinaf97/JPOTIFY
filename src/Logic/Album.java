package Logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Album extends MediaList implements Serializable {
    private String artist;

    public Album(String name,String artist){
        super(name);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }
}
