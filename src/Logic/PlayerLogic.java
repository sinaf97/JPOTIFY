package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PlayerLogic {
    public void loadSong(Media song) throws Exception;
    public void play(String dir) throws Exception;
    public void pause();
    public void fastForward();
    public void fastBackward();
    public void next() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException;
    public void previus() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException;
    public void volumeUp();
    public void volumeDown();
    public void setVolume(int volume);
    public void mute();
    public void enqeuePlaylist(Media song);
    public void deqeuePlaylist(Media song);
    public void jumpTpFrame(int frame);


}
