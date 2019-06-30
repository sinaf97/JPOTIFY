package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * builds and run the player thread in which songs are played alongside other things
 */

public class PlayerThread extends Thread  implements Runnable, Serializable {
    AdvancedPlayer player;
    private Player parent;
    private boolean playing;
    private static PlayerThread self = null;
    private int position = 0;
    private static int temp;
    private static int lastPosition;
    private String dir;
    private boolean flag = true;



    public PlayerThread(int position,String dir,Player parent) throws JavaLayerException {
        super();
        this.parent = parent;
        this.dir = dir;
        self = this;
        playing = true;
        this.position = position;
    }

    public PlayerThread(int position,String dir,Player parent,int j) throws JavaLayerException {
        super();
        temp = j;
        this.parent = parent;
        this.dir = dir;
        self = this;
        playing = true;
        this.position = position;
    }

    @Override
    public void run() {
//            playPlaylist(temp);
        play();
    }


    public void play(){
        try {
            File temp = new File(dir);
            Mp3File temp1 = new Mp3File(dir);
            parent.getUser().setStatus(new Status(temp1.getId3v2Tag().getKey(),true));
            int sampleRate = temp1.getFrameCount();
            double time = temp1.getLengthInSeconds();
            FileInputStream input = null;
            input = new FileInputStream(temp);
            player = new AdvancedPlayer(input);
            if(position == 0) {
                player.play();
            }
            else {
                System.out.println(position);
                try {
                    player.play((int) (position*sampleRate/(time*1000)), 130000);
                    if(player.decodeFrame())
                        parent.next();
                }catch (Exception e){
                    System.out.println(e);
                }
                position = 0;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }



    public int pause() throws InterruptedException {
        int temp = player.getPosition();
        temp += lastPosition;
        lastPosition = temp;
        player.close();
        flag = false;
        return temp;
    }



    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setPosition(int j){
        position = j;
    }

    public static void setLastPosition(int lastPosition) {
        PlayerThread.lastPosition = lastPosition;
    }
}

