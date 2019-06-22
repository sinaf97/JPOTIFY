package UI;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Footer extends JPanel {
    private JpotifyUI jpotifyUI;
    private AdvancedPlayer player;
    private InputStream audioIn;

    public Footer(JpotifyUI jpotifyUI) {
        super();
        this.jpotifyUI = jpotifyUI;
//        audioIn = new InputStream() {
//            @Override
//            public int read() throws IOException {
//                return 0;
//            }
//        };
//        player = new AdvancedPlayer(audioIn);

    }





}
