package server;

import java.util.ArrayList;

public class UserFolder {
    private ArrayList <String> songsName;
    private String folderName;


    public UserFolder(String name){
        this.folderName = name;
        this.songsName = new ArrayList<>();
    }

    public String getName() {
        return this.folderName;
    }

    public ArrayList<String> getSongs() {
        return this.songsName;
    }

    public void addSongName(String newSongName){
        if(!this.songsName.contains(newSongName))
            this.songsName.add(newSongName);
    }

    public void removeSongName(String exSongName){
        this.songsName.remove(exSongName);
    }

    public Boolean songExists(String song){
        return this.songsName.contains(song);
    }

}

