package Logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.*;


public class Player extends Thread implements Singleton,PlayerLogic{
    private static Player self = null;
    private AdvancedPlayer player;
    private FileInputStream input;


    private Player() throws FileNotFoundException, JavaLayerException, InterruptedException {
        super();
        self = this;
    }

    public void run(){
        File temp = new File("/Users/sinafarahani/Desktop/sina.mp3");
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
    }
    @Override
    public void play() throws JavaLayerException {
        player.play(44100);
    }

    @Override
    public void pouse() {
        player.close();
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


    public static Player getInstance() throws FileNotFoundException, JavaLayerException, InterruptedException {
        if(self == null)
            return new Player();
        return self;
    }
}
