package UI;

import javax.swing.*;
import java.awt.*;

public class Center extends JPanel {
    private JpotifyUI jpotifyUI;

    public Center(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
    }
}
