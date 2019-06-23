package Logic;

import com.mpatric.mp3agic.Mp3File;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlayerThread extends Thread  implements Runnable{
    AdvancedPlayer player;
    private boolean playing;
    private static PlayerThread self = null;
    private int position = 0;
    private static int lastPosition;
    private boolean goOn = false;
    private String dir;



    public PlayerThread(String dir) throws JavaLayerException {
        super();
        this.dir = dir;
        self = this;
        playing = true;
    }

    public PlayerThread(int position,String dir) throws JavaLayerException {
        super();
        this.dir = dir;
        self = this;
        playing = true;
        this.position = position;
    }

    @Override
    public void run() {
        try {
            File temp = new File(dir);
            Mp3File temp1 = new Mp3File(dir);
            int sampleRate = temp1.getFrameCount();
            double time = temp1.getLengthInSeconds();
            FileInputStream input = null;
            input = new FileInputStream(temp);
            player = new AdvancedPlayer(input);
            if(position == 0)
                player.play();
            else {
                try {
                    player.play((int) (position*sampleRate/(time*1000)), 130000);
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
        goOn = true;
        return temp;
    }
    public void goOn() throws FileNotFoundException, JavaLayerException {
        start();
    }

    public String getDir() {
        return dir;
    }

    //    public static PlayerThread getInstance() throws JavaLayerException {
//        if(self == null)
//            return new PlayerThread();
//        return self;
//    }
}

