package Logic;


import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;

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
    private MediaList playerPlaylist;
    private int playerPlaylistNumber = 0;
    private FileInputStream input;
    private String dir;
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
        playerPlaylist = new MediaList("");
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
    public void play(String dir) throws JavaLayerException, FileNotFoundException, InterruptedException {
//        if(thread == null) {
//            thread = new PlayerThread(dir,this);
//            thread.start();
//            user.getUi().getFooter().getPlayerUI().initPlayer(dir);
//        }
    }

    public void play() throws JavaLayerException{
        if(thread == null) {
                thread = new PlayerThread(position,dir,this);
                thread.start();
                System.out.println("playing");
        }
        else
            pause();
    }

    public void play(MediaList playlist,int j) throws JavaLayerException{
        if(thread == null) {
            thread = new PlayerThread(position,dir,this,j);
            thread.start();
            System.out.println("playing");
            user.getUi().getFooter().getPlayerUI().initPlayer(playlist.getSongs().get(j));

        }
        else
            pause();
    }

    @Override
    public void pause() {
        try {
            position = thread.pause();
            System.out.println(position);
            thread = null;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void closeAll() {
        try {
            thread.player.close();
            thread.interrupt();
        }catch (Exception e){
//            System.out.println(e);
        }
        thread = null;
    }

    public void changeSong(MediaList playlist,int j) throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        playerPlaylistNumber = j;
        position = 0;
        closeAll();
        this.dir = playlist.getSongs().get(j).getDir();
        play(playlist,j);
        thread.setPosition(0);
        thread.setLastPosition(0);
    }

    public void changeSong(int position) throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        thread.pause();
        this.position = position;
        thread = null;
        play();
        thread.setLastPosition(position);

    }

    public void setPlayerPlaylist(MediaList playlist){
        playerPlaylist = playlist;
    }

    public MediaList getPlayerPlaylist(){
        return playerPlaylist;
    }




    @Override
    public void fastForward() {

    }

    @Override
    public void fastBackward() {

    }

    @Override
    public void next() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        if(playerPlaylistNumber == playerPlaylist.getSongs().size()-1)
            playerPlaylistNumber = -1;
        changeSong(playerPlaylist,++playerPlaylistNumber);
    }

    @Override
    public void previus() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        int j = playerPlaylistNumber;
        if(playerPlaylistNumber == 0)
            playerPlaylistNumber = playerPlaylist.getSongs().size();
        changeSong(playerPlaylist,--playerPlaylistNumber);
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
