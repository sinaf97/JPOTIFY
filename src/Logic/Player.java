package Logic;

import UI.JpotifyUI;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.util.concurrent.TimeUnit;


public class Player extends Thread implements PlayerLogic{
    private static Player self = null;
    private User user;
    private AdvancedPlayer player;
    private FileInputStream input;
    private boolean playing;


    public Player(User user) throws FileNotFoundException, JavaLayerException, InterruptedException {
        super();
        this.user = user;
        self = this;
        playing = false;

    }

    public void run(){
        File temp = new File("/Users/sinafarahani/Desktop/sina.mp3");
        Media temp1 = null;
        try {
            temp1 = new Media("/Users/sinafarahani/Desktop/sina.mp3");
            user.getLibrary().addSong(temp1);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        }
        try {
            input = new FileInputStream(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            player = new AdvancedPlayer(input);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        try {
            player.play(44100);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadSong(Media song) throws FileNotFoundException, JavaLayerException {
        File temp = new File(song.getDir());
        input = new FileInputStream(temp);
        player = new AdvancedPlayer(input);
        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("playing");
//                player.stop();
                System.out.println("stopped");

            }
        });
    }
    @Override
    public void play() throws JavaLayerException, FileNotFoundException {
        loadSong(user.getLibrary().getSongs().get(0));
        playing = true;
        player.play(44100);
        System.out.println("start playing");
    }

    @Override
    public void pause() {
        playing = false;
    }

    @Override
    public void fastForward() {

    }

    @Override
    public void fastBackward() {

    }

    @Override
    public void next() {

    }

    @Override
    public void previus() {

    }

    @Override
    public void volumeUp() {

    }

    @Override
    public void volumeDown() {

    }

    @Override
    public void setVolume(int volume) {

    }

    @Override
    public void mute() {

    }

    @Override
    public void enqeuePlaylist(Media song) {

    }

    @Override
    public void deqeuePlaylist(Media song) {

    }

    @Override
    public void jumpTpFrame(int frame) {

    }


    public static Player getInstance(User user) throws FileNotFoundException, JavaLayerException, InterruptedException {
        if(self == null)
            return new Player(user);
        return self;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    public boolean getPlaying(){
        return this.playing;
    }

    public AdvancedPlayer getPlayer() {
        return player;
    }
}
