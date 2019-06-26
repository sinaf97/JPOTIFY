package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;


import java.io.IOException;

public interface PlayerLogic {
    void play(String dir) throws Exception;
    void pause();
    void next() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException;
    void previus() throws IOException, InterruptedException, JavaLayerException, InvalidDataException, UnsupportedTagException;


}
