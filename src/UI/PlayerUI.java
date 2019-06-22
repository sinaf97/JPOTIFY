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

    public PlayerUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new GridBagLayout());
        JButton play = new JButton("Play");
        JButton pause = new JButton("Pause");
        JButton next = new JButton("Next");
        JButton previous = new JButton("Previous");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Player(jpotifyUI.user).play();
                    System.out.println("out here");
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpotifyUI.user.getPlayer().pause();
            }
        });

        add(play);
        add(pause);

    }
}
