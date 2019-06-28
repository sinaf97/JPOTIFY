package UI;

import Logic.Media;
import Logic.Player;
import Logic.Status;
import Logic.userCommands.Pause_Client;
import Logic.userCommands.Play_Client;
import UI.centerElements.SoundMixer;
import javazoom.jl.decoder.JavaLayerException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JSlider;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static Logic.Media.scale;

public class PlayerUI extends JPanel {
    private JpotifyUI jpotifyUI;
    Player player;
    PlayerSlider playerSlider;
    static ImageIcon playIcon;
    static ImageIcon pauseIcon;
    static ImageIcon nextIcon;
    static ImageIcon previousIcon;
    static{
        String[] dirs = {"play","pause","next","previous"};
        try {
            for (String dir:dirs) {
                BufferedImage bImage = ImageIO.read(new File("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/"+dir+".png"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                byte[] data = bos.toByteArray();
                if(dir.equals("play"))
                    playIcon = new ImageIcon(scale(data, 30, 30,new Color(238,238,238)));
                else if(dir.equals("pause"))
                    pauseIcon = new ImageIcon(scale(data, 30, 30,new Color(238,238,238)));
                else if(dir.equals("next"))
                    nextIcon = new ImageIcon(scale(data, 30, 30,new Color(238,238,238)));
                else
                    previousIcon = new ImageIcon(scale(data, 30, 30,new Color(238,238,238)));
            }

        }catch (Exception e){
            new ShowError("Icons files can;t be found.");
        }
    }

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
        JButton play = new JButton();
        play.setToolTipText("Pause");
        play.setIcon(pauseIcon);
        JButton next = new JButton();
        next.setToolTipText("Next");
        next.setIcon(nextIcon);
        JButton previous = new JButton();
        previous.setToolTipText("Previous");
        previous.setIcon(previousIcon);
//        play.setPreferredSize(new Dimension(50,50));
//        play.setIcon(playIcon);
        play.addActionListener(e -> {
            if(play.getToolTipText().equals("Pause")) {
                jpotifyUI.user.setStatus(new Status(song.getName(),false));
                Pause_Client toServer = new Pause_Client(jpotifyUI.getUser());
                try {
                    toServer.pauseAction();
                }catch (Exception e1){
                    new ShowError("Server did not respond.");
                }
                play.setToolTipText("Play");
                play.setIcon(playIcon);
                player.pause();
                playerSlider.sliderThread.setPause(true);

            }
            else {
                jpotifyUI.user.setStatus(new Status(song.getName(),true));
                Play_Client toServer = new Play_Client(jpotifyUI.getUser());
                try {
                    toServer.playAction();
                }catch (Exception e2){
                    new ShowError("Server did not respond");
                }
                play.setToolTipText("Pause");
                play.setIcon(pauseIcon);
                playerSlider.sliderThread.setPause(false);
                try {
                    player.play();

                } catch (JavaLayerException e1) {
                    new ShowError("Playing the song has encountered an issue.");
                }
            }
        });

        next.addActionListener(e -> {
            try {
                player.next();
            } catch (Exception e1) {
                new ShowError("An error has occurred while going to the next song.");
            }
        });

        previous.addActionListener(e -> {
            try {
                player.previus();
            } catch (Exception e1) {
                new ShowError("An error has occurred while going to the previus song.");}
        });


        JToolBar temp = new JToolBar();
        temp.add(previous);
        temp.add(play);
        temp.add(next);
        JPanel controlsAndVolume = new JPanel();
        try {
            add(makeTitle(song), BorderLayout.WEST);
        }catch (Exception e){
//            new ShowError("Song;s information failed to load.");
        }
        playerSlider = PlayerSlider.getInstance(jpotifyUI,song);
        try {
            playerSlider.update(song.getLength());
        }catch (Exception e){
//            new ShowError("Slider can't function normally");
        }
        controlsAndVolume.setLayout(new GridLayout(1,3));
        controlsAndVolume.add(temp);
        controlsAndVolume.add(playerSlider);
        add(controlsAndVolume);
        add(makeVolume(),BorderLayout.EAST);
        try {
            jpotifyUI.getFooter().addArtWork(song);
        }catch (Exception e){
//            new ShowError("Artwork loading failed");
        }
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
            new ShowError("getting system volume failed.");
        }
        temp.addChangeListener(e -> {

            String[] commands = {"osascript", "-e","set volume " + (temp.getValue()/14)};
            try {
                new ProcessBuilder(commands).start();
            } catch (IOException e1) {
                new ShowError("Changing volume failed");
            }
        });
        result.add(new JLabel("Volume: "));
        result.add(temp);
        return result;
    }

    public JPanel makeTitle(Media song){
        JPanel temp = new JPanel();
        temp.setLayout(new GridLayout(3,1));
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
        slider.setSize(300,50);
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
            }catch (Exception e1){
//                new ShowError("Initializing player slider failed.");
            }
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
                    new ShowError("Player slider moving failed");
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
