package Logic;

import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;

public interface PlayerLogic {
    public void loadSong(Media song) throws Exception;
    public void play() throws Exception;
    public void pause();
    public void fastForward();
    public void fastBackward();
    public void next();
    public void previus();
    public void volumeUp();
    public void volumeDown();
    public void setVolume(int volume);
    public void mute();
    public void enqeuePlaylist(Media song);
    public void deqeuePlaylist(Media song);
    public void jumpTpFrame(int frame);


}
