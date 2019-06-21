package Logic;


import UI.JpotifyUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Library {
    User user;
    ArrayList<Media> songs;
    HashMap<String,Album> albums;
    HashMap<String,MediaList> playlists;

    public Library(User user){
        this.user = user;
        this.playlists = new HashMap<>();
        this.songs = new ArrayList<>();
        this.albums = new HashMap<>();
        this.playlists.put("Sharable Playlist",new SharablePlayList());
        this.playlists.put("Favorite Songs",new FavoriteSongs());
    }

    public void addSong(Media newSong){
        if(!songs.contains(newSong))
            this.songs.add(newSong);
        else
            return;
        String album = newSong.getAlbum();
        if(albums.containsKey(album))
            addToMedialist(newSong,albums.get(album));
        else{
            Album newAlbum = new Album(album,newSong.getArtist());
            addToMedialist(newSong,newAlbum);
            albums.put(newAlbum.getName(),newAlbum);
        }

    }
    public void removeSong(Media exsong){
        if(!songs.contains(exsong))
            return;
        for (Map.Entry<String, Album> entry : albums.entrySet())
            if(entry.getValue().songExists(exsong))
                entry.getValue().removeSong(exsong);
        for (Map.Entry<String, MediaList> entry : playlists.entrySet())
            if(!entry.getKey().equals("Sharable Playlist") && entry.getValue().songExists(exsong))
                entry.getValue().removeSong(exsong);
        songs.remove(exsong);
    }
    public void addPlayList(String name){
        if(!playlists.containsKey(name))
            playlists.put(name,new MediaList(name));
    }
    public void removePlayList(MediaList playList){
        if(playlists.containsValue(playList))
            playlists.remove(playList.getName());
    }
    public void addToMedialist(Media newSong,MediaList mediaList){
        if(!mediaList.songExists(newSong))
            mediaList.addSong(newSong);
    }
    public void removeFromMediaList(Media exSong,MediaList mediaList){
        if(mediaList.songExists(exSong))
            mediaList.removeSong(exSong);
    }

    public ArrayList<Media> searchSong(String name){
        ArrayList<Media> result = new ArrayList<>();
        for (Media m:songs)
            if(m.getName().toLowerCase().contains(name.toLowerCase()))
                result.add(m);
        return result;
    }
}