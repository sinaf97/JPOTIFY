package UI.centerElements;

import Logic.Media;
import UI.JpotifyUI;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SongsUI extends JPanel{
    private JpotifyUI jpotifyUI;

    public SongsUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        int i = 1;
        for(Media song:jpotifyUI.getUser().getLibrary().getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song));

        }

        jpotifyUI.getMain().updateUI();


    }

    private class Song extends JPanel{

        public Song(Media song){
            super();
            setLayout(new GridLayout(1,4));
            JButton play = new JButton("Play");
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        jpotifyUI.getUser().getPlayer().changeSong(song.getDir());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            add(play);
            add(new JLabel(song.getName()));
            add(new JLabel(song.getArtist()));
            add(new JLabel(song.getAlbum()));
        }
    }
}
