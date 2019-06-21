package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Header extends JPanel {
    private JpotifyUI jpotifyUI;

    public Header(JpotifyUI jpotifyUI){
        super();
        setLayout(new BorderLayout());
        JLabel username = new JLabel("username");
        JTextField searchBox = new JTextField();
        JLabel search = new JLabel("Search: ");
        add(username,BorderLayout.WEST);
        JPanel searchArea = new JPanel();
        searchArea.setLayout(new BorderLayout());
        searchArea.add(search,BorderLayout.WEST);
        searchArea.add(searchBox,BorderLayout.CENTER);
        add(searchArea,BorderLayout.CENTER);
        this.jpotifyUI = jpotifyUI;

        searchBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchBox.getText();

            }
        });
    }
}
