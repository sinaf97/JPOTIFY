package UI;

import javax.swing.*;
import java.awt.*;

public class Left extends JPanel {
    private Library library;
    private PlaylistUI playlist;
    private JpotifyUI jpotifyUI;

    public Left(JpotifyUI jpotifyUI){
        super();
        BorderLayout layout = new BorderLayout();
        layout.setVgap(10);
        setLayout(layout);
        library = new Library(jpotifyUI);
        playlist = new PlaylistUI(jpotifyUI);
        add(library,BorderLayout.NORTH);
        add(playlist);
        setBackground(Color.GRAY);
        this.jpotifyUI = jpotifyUI;
    }

    public static JLabel makeTitle(String title){
        JLabel temp = new JLabel(title);
        temp.setHorizontalAlignment(0);
        temp.setFont(new Font(temp.getFont().getName(),Font.BOLD,15));
        temp.setForeground(Color.white);
        temp.setBackground(Color.black);
        temp.setOpaque(true);
        temp.setBorder(BorderFactory.createLineBorder(Color.black));
        return temp;
    }
}
