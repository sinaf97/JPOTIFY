package Logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * base class of all playlists such as playlists,albums,shared plauylist,favorite songs
 */

public class MediaList implements Serializable {
    private ArrayList<Media> songs;
    private String name;


    public MediaList(String name){
        this.name = name;
        songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


    public ArrayList<Media> getSongs() {
        return songs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSong(Media newSong){
        if(!songs.contains(newSong))
            songs.add(newSong);
    }
    public void removeSong(Media exSong){
        if(songs.contains(exSong))
            songs.remove(exSong);
    }
    public Boolean songExists(Media song){
        return songs.contains(song)?true:false;
    }


}
