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

/**
 * implement logic of the player
 *
 * @author  Sina Farahani, MohammadReza Ghoreighi
 * @version 1.0
 * @since   2019-05-01
 */
public class Player implements Singleton, PlayerLogic{
    private static Player self = null;
    private User user;
    private AdvancedPlayer player;
    private FileInputStream input;
    private PlayerThread thread;
    private boolean playing;
    private int position;

    /**
     *
     * @param user
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws InterruptedException
     */
    private Player(User user) throws FileNotFoundException, JavaLayerException, InterruptedException {
        this.user = user;
        self = this;
        String s = new String();


    }


    /**
     *
     *
     * @param song
     * @throws FileNotFoundException
     * @throws JavaLayerException
     */
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
    public void play(String dir) throws JavaLayerException{
        if(thread == null) {
            if(position == 0) {
                thread = new PlayerThread(dir);
                thread.start();
            }
            else{
                thread = new PlayerThread(position,dir);
                thread.start();
            }
        }
        else
            pause();
    }

    @Override
    public void pause() {
        try {
            position = thread.pause();
            thread = null;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void closeAll() {
        position = 0;
        try {
            thread.player.close();
        }catch (Exception e){}
        thread = null;
    }

    public void changeSong(String dir) throws FileNotFoundException, InterruptedException, JavaLayerException {
        closeAll();
        user.getUi().getFooter().getPlayerUI().initPlayer(dir);
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

    public PlayerThread getThread() {
        return thread;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
