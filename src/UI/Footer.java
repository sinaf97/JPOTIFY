package UI;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Footer extends JPanel {
    private JpotifyUI jpotifyUI;
    private PlayerUI playerUI;

    public Footer(JpotifyUI jpotifyUI) throws FileNotFoundException, InterruptedException, JavaLayerException {
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BorderLayout());
        playerUI = new PlayerUI(jpotifyUI);
        add(playerUI);
    }

    public PlayerUI getPlayerUI() {
        return playerUI;
    }
}
