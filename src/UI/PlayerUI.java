package UI;

import Logic.Player;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class PlayerUI extends JPanel {
    private JpotifyUI jpotifyUI;
    String dir;
    ImageIcon playIcon;
    ImageIcon pauseIcon;
    Player player;

    public PlayerUI(JpotifyUI jpotifyUI) throws FileNotFoundException, InterruptedException, JavaLayerException {
        super();
        this.jpotifyUI = jpotifyUI;
        player = Player.getInstance(jpotifyUI.user);
        initPlayer(null);
    }

    public void initPlayer(String dir) throws FileNotFoundException, InterruptedException, JavaLayerException {
        removeAll();
        this.dir = dir;
        if(dir!=null)
            player.play(dir);
//        playIcon = new ImageIcon("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/play.png", "Play");
//        pauseIcon = new ImageIcon("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/pause.png", "Play");
        setLayout(new BorderLayout());
        JButton play = new JButton("Pause");
        JButton next = new JButton("Next");
        JButton previous = new JButton("Previous");
//        play.setPreferredSize(new Dimension(50,50));
//        play.setIcon(playIcon);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(play.getText().equals("Pause")) {
                    play.setText("Play");
                    player.pause();
                }
                else {
                    play.setText("Pause");
                    try {
                        player.play(dir);
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JToolBar temp = new JToolBar();
        temp.add(previous);
        temp.add(play);
        temp.add(next);
        add(temp);
        updateUI();
    }

    public Player getSina() {
        return player;
    }
}
