package Logic;

import java.util.ArrayList;

public class MediaList {
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
