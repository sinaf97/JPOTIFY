package UI;

import Logic.MediaList;

import javax.swing.*;
import java.awt.*;

public class Playlist extends JPanel {
    private JpotifyUI jpotifyUI;

    public Playlist(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        setBackground(Color.black);

        JLabel title = Left.makeTitle("Playlists");
        JPanel library = makePlaylistSection();
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(library,BorderLayout.NORTH);
        add(title,BorderLayout.NORTH);
        container.setBackground(Color.black);
        add(container,BorderLayout.CENTER);
    }



    private JPanel makePlaylistSection() {
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JButton add = makeAddButton();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        temp.add(add,c);
        int i = 1;
        for(MediaList playlist:jpotifyUI.user.getLibrary().getPlaylists().values()){
            JButton playListButton = makeCustomButton(playlist);
            c.gridy = i++;
            temp.add(playListButton,c);
        }
        temp.setBackground(Color.black);
        return temp;
    }

    private JButton makeAddButton() {
        JButton temp = new JButton("Add Playlist");
        /*
        implement action listener here
         */
        return temp;
    }

    private JButton makeCustomButton(MediaList playlist){
        JButton temp = new JButton(playlist.getName());
        /*
        implement action listener here
         */
        return temp;
    }


}
