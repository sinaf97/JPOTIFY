package UI;

import Logic.Media;
import UI.centerElements.SongsUI;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Library extends JPanel {
    private JpotifyUI jpotifyUI;


    public Library(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        setBackground(Color.black);

        JLabel title = Left.makeTitle("Library");
        JPanel library = makeLibrarySection();

        add(title,BorderLayout.NORTH);
        add(library,BorderLayout.CENTER);
    }


    private JPanel makeLibrarySection() {
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        JButton add = makeAddButton();
        JButton songs = makeSongsButton();
        JButton albums = makeAlbumsButton();
        c.gridy = 0;
        c.gridx = 0;
        temp.add(add,c);
        c.gridy = 1;
        temp.add(songs,c);
        c.gridy = 2;
        temp.add(albums,c);
        temp.setBackground(Color.black);
        return temp;
    }

    private JButton makeAddButton() {
        JButton temp = new JButton("Add To Library");
            JFileChooser chooser = new JFileChooser();
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooser.showDialog(chooser, "Open");
                    try {
                        jpotifyUI.user.getLibrary().addSong(new Media(chooser.getSelectedFile().getAbsolutePath()));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }});
            return temp;
        }

    private JButton makeSongsButton() {
        JButton temp = new JButton("Songs");
        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpotifyUI.getMain().removeAll();
                jpotifyUI.getMain().add(new SongsUI(jpotifyUI));
            }
        });
        return temp;
    }

    private JButton makeAlbumsButton() {
        JButton temp = new JButton("Albums");
        /*
        implement action listener here
         */
        return temp;
    }


}
