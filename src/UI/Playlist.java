package UI;

import javax.swing.*;

public class Playlist extends JPanel {
    private JpotifyUI jpotifyUI;

    public Playlist(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
    }

    public Playlist(String sharable_playlist) {
    }
}
