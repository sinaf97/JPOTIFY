package UI.centerElements;

import Logic.Media;
import Logic.MediaList;
import Logic.SongSerial;
import Logic.userCommands.Download_Client;
import Logic.userCommands.Upload_Client;
import UI.JpotifyUI;
import UI.ShowError;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SharedSongsUI extends JPanel{
    private JpotifyUI jpotifyUI;

    public SharedSongsUI(JpotifyUI jpotifyUI,ArrayList<String> playlist,String friendUsername,String mode){
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        int j = 0;
        for(String song:playlist) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,j,friendUsername,"Shared Playlist"));
            j++;

        }
        jpotifyUI.getMain().updateUI();


    }

    private class Song extends JPanel{


        public Song(String song,int j,String friendUsername,String mode){
            super();
            setLayout(new GridLayout(1,5));
            JButton play = new JButton("Play");
            play.addActionListener(e -> {
                try {
                    jpotifyUI.getUser().getPlayer().setPlayerPlaylist(null);
                    Download_Client playOnline = new Download_Client(jpotifyUI.getUser());
                    File temp = new File("temp/temp.mp3");
                    playOnline.download(temp,friendUsername,song);
                    Media newSong = new Media(temp.getAbsolutePath());
                    jpotifyUI.getUser().getPlayer().play(newSong.getDir());
                } catch (Exception e1) {
                    new ShowError("Loading the song failed.");
                }
            });

            JButton download = new JButton("Download");
            play.addActionListener(e -> {
                try {
                    jpotifyUI.getUser().getPlayer().setPlayerPlaylist(null);
                    Download_Client downloadSong = new Download_Client(jpotifyUI.getUser());
                    File temp = new File("downloaded/"+song);
                    downloadSong.download(temp,friendUsername,song);
                    Media newSong = new Media(temp.getAbsolutePath());
                    jpotifyUI.getUser().getLibrary().addSong(newSong);
                    jpotifyUI.getUser().getPlayer().play(newSong.getDir());
                } catch (Exception e1) {
                    new ShowError("Downloading the song failed.");
                }
            });


            setLayout(new GridLayout(1,2));
            JPanel container1 = new JPanel();
            container1.add(play);
            container1.add(download);
            JPanel container2 = new JPanel();
            container2.setLayout(new GridLayout(3,1));
            JLabel temp = new JLabel(song);
            JLabel temp2 = new JLabel("Artist: "+song);
            JLabel temp3 = new JLabel("Album: "+song);
            temp.setFont(new Font("timesnewroman",Font.BOLD,14));
            temp2.setFont(new Font("timesnewroman",Font.PLAIN,12));
            temp3.setFont(new Font("timesnewroman",Font.PLAIN,10));
            container2.add(temp);
            container2.add(temp2);
            container2.add(temp3);
            add(container1);
            add(container2);

        }

        public void addSongToPlaylist(Media song){
            JFrame selectPlaylist = new JFrame("Select Playlist");
            Object[] names = jpotifyUI.getUser().getLibrary().getPlaylists().keySet().toArray();
            selectPlaylist.setSize(new Dimension(300,names.length*20+100));
            JPanel container = new JPanel();
            JList playlists = new JList(names);
            JButton add = new JButton("Add");
            add.addActionListener(e -> {
                jpotifyUI.getUser().getLibrary().addToMedialist(song,(String) playlists.getSelectedValue());
                selectPlaylist.setVisible(false);
            });
            container.setLayout(new BoxLayout(container,3));
            container.add(playlists);
            container.add(add);
            selectPlaylist.add(container);
            selectPlaylist.setVisible(true);
        }

        public void removeSongFromPlaylist(Media song,MediaList playlist){
            jpotifyUI.getUser().getLibrary().removeFromMediaList(song,playlist);
            jpotifyUI.getMain().updateUI();

        }

    }

}
