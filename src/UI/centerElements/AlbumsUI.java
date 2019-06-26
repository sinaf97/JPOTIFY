package UI.centerElements;

import Logic.Album;
import Logic.Media;
import UI.JpotifyUI;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Map;

public class AlbumsUI extends JPanel{
    private JpotifyUI jpotifyUI;

    public AlbumsUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        int i = 1;

        for (Map.Entry<String, Album> entry : jpotifyUI.getUser().getLibrary().getAlbums().entrySet()) {
            setLayout(new GridLayout(i++,1));
            add(new AlbumUI(entry.getValue()));

        }
        jpotifyUI.getMain().updateUI();


    }

    private class AlbumUI extends JPanel{

        public AlbumUI(Album album){
            super();
            setLayout(new GridLayout(1,3));
            JButton browse = new JButton("Browse Songs");
            browse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jpotifyUI.getMain().removeAll();
                    jpotifyUI.getMain().setLayout(new FlowLayout());
                    jpotifyUI.getMain().add(new SongsUI(jpotifyUI,album.getSongs()));
                }
            });
            add(browse);
            add(new JLabel(album.getName()));
            add(new JLabel(album.getArtist()));
        }
    }
}
