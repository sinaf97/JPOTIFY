package UI;

import Logic.MediaList;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class PlaylistUI extends JPanel {
    private JpotifyUI jpotifyUI;

    public PlaylistUI(JpotifyUI jpotifyUI){
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
        JButton temp = new JButton("Add PlaylistUI");
        JFileChooser chooser = new JFileChooser();
        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.showDialog(chooser,"Open");
                try {
                    jpotifyUI.user.getPlayer().changeSong(chooser.getSelectedFile().getAbsolutePath());

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
