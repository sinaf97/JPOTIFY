package Logic;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.*;

public class Media {
    private String dir;
    private String name;
    private String artist;
    private String album;
    private byte[] artWork;

    public Media(String dir) throws IOException, InvalidDataException, UnsupportedTagException {
        this.dir = dir;
        File song = new File(dir);
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(song));
        long length = song.length();
        input.skip(length-128);
        byte[] last128 = new byte[128];
        input.read(last128);
        String info = new String(last128);
        String tag = info.substring(0, 3);
        if (tag.equals("TAG")){
            this.name = info.substring(3, 33);
            this.artist = info.substring(33, 63);
            this.album = info.substring(63, 93);
        }
        input.close();
        Mp3File mp3file = new Mp3File(dir);
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            artWork = id3v2Tag.getAlbumImage();
        }

}

    public String getDir() {
        return dir;
    }

    public String getName(){
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }
    public String[] getInfo(){
        return new String[]{name, artist, album};
    }
}