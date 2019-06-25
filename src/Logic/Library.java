package Logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class Library holds all the records of songs and albums and playlists in it
 */

public class Library {
    User user;
    ArrayList<Media> songs;
    HashMap<String,Album> albums;
    LinkedHashMap<String,MediaList> playlists;

    public Library(User user){ //Constructor
        this.user = user;
        this.playlists = new LinkedHashMap<>();
        this.playlists.put("Favorite  Songs",new MediaList("Favorite Songs"));
        this.songs = new ArrayList<>();
        this.albums = new HashMap<>();
        this.playlists.put("Sharable PlaylistUI",new SharablePlayList());
    }

    /**
     * adds a song to songs and album simoultaniusly if the song is new
     * @param newSong
     */

    public void addSong(Media newSong){
        for (Media m:songs)
            if(m.getDir().equals(newSong.getDir()))
                return;
        songs.add(newSong);
        String album = newSong.getAlbum();
        if(albums.containsKey(album))
            addToMedialist(newSong,albums.get(album));
        else{
            Album newAlbum = new Album(album,newSong.getArtist());
            addToMedialist(newSong,newAlbum);
            albums.put(newAlbum.getName(),newAlbum);
        }

    }

    /**
     * removes a song from songs and album simoultaniusly if the song exists
     * @param exsong
     */
    public void removeSong(Media exsong){
        if(!songs.contains(exsong))
            return;
        for (Map.Entry<String, Album> entry : albums.entrySet())
            if(entry.getValue().songExists(exsong))
                entry.getValue().removeSong(exsong);
        for (Map.Entry<String, MediaList> entry : playlists.entrySet())
            if(!entry.getKey().equals("Sharable PlaylistUI") && entry.getValue().songExists(exsong))
                entry.getValue().removeSong(exsong);
        songs.remove(exsong);
    }
    public void addPlayList(String name) throws ExistException{
        if(!playlists.containsKey(name))
            playlists.put(name,new MediaList(name));
        else
            throw new ExistException();
    }
    public void removePlayList(MediaList playList){
        if(playlists.containsValue(playList))
            playlists.remove(playList.getName());
    }
    public void addToMedialist(Media newSong,MediaList mediaList){
        if(!mediaList.songExists(newSong))
            mediaList.addSong(newSong);
    }

    public void addToMedialist(Media newSong,String name){
        MediaList mediaList = playlists.get(name);
        if(!mediaList.songExists(newSong))
            mediaList.addSong(newSong);
    }
    public void removeFromMediaList(Media exSong,MediaList mediaList){
        if(mediaList.songExists(exSong))
            mediaList.removeSong(exSong);
    }

    /**
     * finds songs which contains the string passed to it, ignoring upper or lowercase
     * @param name
     * @return list of matched songs names
     */

    public ArrayList<String> searchSong(String name){
        ArrayList<String> result = new ArrayList<>();
        for (Media m:songs)
            try {
                if (m.getName().toLowerCase().contains(name.toLowerCase()))
                    result.add(m.getName());
            }catch (Exception e){}
        return result;
    }

    public ArrayList<Media> getSearchSongResult(String name){
        ArrayList<Media> result = new ArrayList<>();
        for (Media m:songs)
            try {
                if (m.getName().toLowerCase().contains(name.toLowerCase()))
                    result.add(m);
            }catch (Exception e){}
        return result;
    }

    public ArrayList<Media> getSongs() {
        return songs;
    }

    public HashMap<String, Album> getAlbums() {
        return albums;
    }

    public HashMap<String, MediaList> getPlaylists() {
        return playlists;
    }

    public ArrayList<Media> getAlbumSongs(Album album){
        ArrayList<Media> result = new ArrayList<>();
        for (Media song:album.getSongs())
                result.add(song);
        return result;
    }
}
