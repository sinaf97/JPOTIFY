import Logic.Player;
import Logic.Status;
import Logic.User;
import Logic.Media;
import UI.JpotifyUI;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException, JavaLayerException, InterruptedException {
        User t1 = new User("sinaf97","Sina Farahani");
        User t2 = new User("mori","Mori Farahani");
        User t3 = new User("mahsa","Mahsa Farahani");
        User t4 = new User("mary","Maryam Farahani");
        User t5 = new User("mitra","Mitra Farahani");
        t2.setOnline(true);
        t3.setOnline(false);
        t4.setOnline(true);
        t5.setOnline(true);
        t1.getFriends().addFriend(t2);
        t1.getFriends().addFriend(t3);
        t1.getFriends().addFriend(t4);
        t1.getFriends().addFriend(t5);
        Media song = new Media("/Users/sinafarahani/Desktop/show.mp3");
        t1.getLibrary().addSong(song);
        song = new Media("/Users/sinafarahani/Desktop/sina.mp3");
        t1.getLibrary().addSong(song);
        song = new Media("/Users/sinafarahani/Desktop/mamad.mp3");
        t1.getLibrary().addSong(song);
        t2.setStatus(new Status(song));
        t3.setStatus(new Status(song,false));
        t4.setStatus(new Status(song,false));
        t5.setStatus(new Status(song,true));
        t1.setUi( new JpotifyUI(t1));

    }
}