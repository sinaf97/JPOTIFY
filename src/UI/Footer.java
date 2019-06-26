package UI;

import Logic.Media;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;


public class Footer extends JPanel {
    private JpotifyUI jpotifyUI;
    private PlayerUI playerUI;
    private JLabel icon;

    public Footer(JpotifyUI jpotifyUI) throws FileNotFoundException, InterruptedException, JavaLayerException {
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        playerUI = new PlayerUI(jpotifyUI);
        add(playerUI);

    }


    public void addArtWork(Media song){
        icon = new JLabel("");
        icon.setPreferredSize(new Dimension(50,50));
        icon.setIcon(song.getArtWork());
        removeAll();
        add(icon,BorderLayout.WEST);
        add(playerUI);
        updateUI();
    }
    public PlayerUI getPlayerUI() {
        return playerUI;
    }
}
