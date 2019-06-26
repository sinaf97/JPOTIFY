package UI;

import Logic.Media;
import Logic.Player;
import UI.centerElements.SoundMixer;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JSlider;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerUI extends JPanel {
    private JpotifyUI jpotifyUI;
    ImageIcon playIcon;
    ImageIcon pauseIcon;
    Player player;
    PlayerSlider playerSlider;

    public PlayerUI(JpotifyUI jpotifyUI) throws FileNotFoundException, InterruptedException, JavaLayerException {
        super();
        this.jpotifyUI = jpotifyUI;
        player = Player.getInstance(jpotifyUI.user);

        initPlayer(null);
    }

    public void initPlayer(Media song){
        removeAll();
//        playIcon = new ImageIcon("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/play.png", "Play");
//        pauseIcon = new ImageIcon("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/pause.png", "Play");
        setLayout(new BorderLayout());
        JButton play = new JButton("Pause");
        JButton next = new JButton("Next");
        JButton previous = new JButton("Previous");
//        play.setPreferredSize(new Dimension(50,50));
//        play.setIcon(playIcon);
        play.addActionListener(e -> {
            if(play.getText().equals("Pause")) {
                play.setText("Play");
                player.pause();
                playerSlider.sliderThread.setPause(true);

            }
            else {
                play.setText("Pause");
                playerSlider.sliderThread.setPause(false);
                try {
                    player.play();

                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                }
            }
        });

        next.addActionListener(e -> {
            try {
                player.next();
            } catch (Exception e1) {
                e1.printStackTrace();}
        });

        previous.addActionListener(e -> {
            try {
                player.previus();
            } catch (Exception e1) {
                e1.printStackTrace();}
        });


        JToolBar temp = new JToolBar();
        temp.add(previous);
        temp.add(play);
        temp.add(next);
        JPanel controlsAndVolume = new JPanel();
        try {
            add(makeTitle(song), BorderLayout.WEST);
        }catch (Exception e){}
        playerSlider = PlayerSlider.getInstance(jpotifyUI,song);
        try {
            playerSlider.update(song.getLength());
        }catch (Exception e){}
        controlsAndVolume.setLayout(new GridLayout(1,3));
        controlsAndVolume.add(temp);
        controlsAndVolume.add(playerSlider);
        add(controlsAndVolume);
        add(makeVolume(),BorderLayout.EAST);
        updateUI();
    }

    public Player getSina() {
        return player;
    }

    public PlayerSlider getPlayerSlider() {
        return playerSlider;
    }

    public JPanel makeVolume(){
        JPanel result = new JPanel();
        JSlider temp = new JSlider(0,100);
        String[] getVolume = {"osascript", "-e","output volume of (get volume settings)"};

        try {
            ProcessBuilder me = new ProcessBuilder(getVolume);
            Process process =  me.start();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null)
            {
                temp.setValue(Integer.decode(s));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        temp.addChangeListener(e -> {

            String[] commands = {"osascript", "-e","set volume " + (temp.getValue()/14)};
            try {
                new ProcessBuilder(commands).start();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        result.add(new JLabel("Volume: "));
        result.add(temp);
        return result;
    }

    public JPanel makeTitle(Media song){
        JPanel temp = new JPanel();
        temp.setLayout(new GridLayout(1,2));
        JLabel icon = new JLabel("");
        icon.setIcon(song.getArtWork());
        temp.add(icon);
        temp.add(new JLabel(song.getName()));
        temp.add(new JLabel(song.getArtist()));
        temp.add(new JLabel(song.getAlbum()));
        return temp;
    }

}

class PlayerSlider extends JPanel {
    private JpotifyUI jpotifyUI;
    protected JSlider slider;
    protected SliderThread sliderThread;
    protected JLabel time;
    protected int back;
    private static PlayerSlider self=null;
    private int position=0;


    private PlayerSlider(JpotifyUI jpotifyUI,Media song) {
        super();
        self = this;
        slider = new JSlider();
        time = new JLabel("00:00");
        slider.setMinimum(0);
        slider.setValue(0);
        try{
            slider.setMaximum(song.getLength());
        }catch (Exception e){slider.setMaximum(100);}
        this.jpotifyUI = jpotifyUI;
        initPlayerSlider();
        add(time);
        add(slider);

    }

    public SliderThread getSliderThread() {
        return sliderThread;
    }

    private void initPlayerSlider(){
        try {
            sliderThread.interrupt();
        }catch (Exception e){
            System.out.println(e);
        }
        slider.addChangeListener(e -> {
            try{
                if(slider.getValue()-back>1 || slider.getValue()-back<-1) {
                    position = slider.getValue();
                    back = position;
                    jpotifyUI.getUser().getPlayer().changeSong(slider.getValue() * 1000);
                    position = 0;

                }
            }catch (Exception e1){}
        });
        sliderThread = new SliderThread(this);
        sliderThread.start();
    }

    public class SliderThread extends Thread{
        private PlayerSlider playerSlider;
        private boolean pause;
        public SliderThread(PlayerSlider playerSlider) {
            super();
            this.playerSlider = playerSlider;
        }
        public void run(){
            int c = 0;
            while (playerSlider.slider.getValue()<playerSlider.slider.getMaximum()){
                try {
                    sleep(1000);
                    c = playerSlider.slider.getValue();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                if(pause)
                    continue;

                back = c;
                playerSlider.slider.setValue(++c);
                playerSlider.time.setText(getTime(c));
                playerSlider.time.updateUI();
                playerSlider.updateUI();
            }

        }

        public  String getTime(int c){
            int min;
            int sec;
            min = c/60;
            sec = c-min*60;
            if(sec<10&&min<10)
                return "0"+min +":0"+sec;
            else if(sec>=10&&min<10)
                return "0"+min +":"+sec;
            else if(sec<10&&min>=10)
                return min +":0"+sec;
            else
                return min +":"+sec;
        }

        public void setPause(boolean pause) {
            this.pause = pause;
        }
    }


    public void update(int end){
        slider.setMinimum(0);
        if(position==0) {
            slider.setValue(0);
            back = 0;
        }
        else {
            slider.setValue(position);
            back = position;
        }
        slider.setMaximum(end);
        time.setText(sliderThread.getTime(position));
        position = 0;
        time.updateUI();
        updateUI();
    }

    public static PlayerSlider getInstance(JpotifyUI jpotifyUI,Media media){
        if(self == null)
            return new PlayerSlider(jpotifyUI,media);
        return self;
    }

}
