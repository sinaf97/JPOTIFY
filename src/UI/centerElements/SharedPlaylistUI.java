package UI.centerElements;

import Logic.Album;
import Logic.MediaList;
import UI.JpotifyUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class SharedPlaylistUI extends JPanel {

    private JpotifyUI jpotifyUI;

    public SharedPlaylistUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        int i = 1;
        /*
        get users with shared playlist
         */
        ArrayList<MediaList> shared = new ArrayList<>();
        for (MediaList share: shared) {
            setLayout(new GridLayout(i++,1));
            add(new SharedplaylistUI(share));

        }
        jpotifyUI.getMain().updateUI();


    }

    private class SharedplaylistUI extends JPanel {

        public SharedplaylistUI(MediaList Sharedplaylist){
            super();
            setLayout(new GridLayout(1,3));
            JButton browse = new JButton("Browse Songs");
            browse.addActionListener(e -> {
                jpotifyUI.getMain().removeAll();
                jpotifyUI.getMain().setLayout(new FlowLayout());
                /*
                fetch playlist from server
                name of the playlist is the user which has the song
                 */
                jpotifyUI.getMain().add(new SongsUI(jpotifyUI,Sharedplaylist,"Shared Playlist"));
            });
            add(browse);
            add(new JLabel(Sharedplaylist.getName()));
        }
    }
}
