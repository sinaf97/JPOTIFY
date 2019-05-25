package UI;

import javax.swing.*;
import java.awt.*;

public class Left extends JPanel {
    private Library library;
    private Playlist playlist;

    public Left(){
        super();
        setLayout(new GridLayout(2,1));
        library = new Library();
        playlist = new Playlist();
        add(library);
        add(playlist);
//        setBackground(Color.YELLOW);
    }
}
