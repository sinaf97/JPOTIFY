package Logic;

import com.mpatric.mp3agic.Mp3File;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.PlayerApplet;
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



    public PlayerThread() throws JavaLayerException {
        super();
        self = this;
        playing = true;
    }

    public PlayerThread(int position) throws JavaLayerException {
        super();
        self = this;
        playing = true;
        this.position = position;
    }

    @Override
    public void run() {
        try {
            File temp = new File("/Users/sinafarahani/Desktop/sina.mp3");
            Mp3File temp1 = new Mp3File("/Users/sinafarahani/Desktop/sina.mp3");
            int sampleRate = temp1.getFrameCount();
            double time = temp1.getLengthInSeconds();
            System.out.println(temp1.getStartOffset() + " " + time);
            FileInputStream input = null;
            input = new FileInputStream(temp);
            player = new AdvancedPlayer(input);
            if(position == 0)
                player.play();
            else {
                try {
                    System.out.println(position+lastPosition);
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
        System.out.println("temp is: "+temp);
        temp += lastPosition;
        System.out.println("now temp is: "+temp);
        System.out.println("last position is: "+lastPosition);
        lastPosition = temp;
        player.close();
        goOn = true;
        return temp;
    }
    public void goOn() throws FileNotFoundException, JavaLayerException {
        start();
    }

//    public static PlayerThread getInstance() throws JavaLayerException {
//        if(self == null)
//            return new PlayerThread();
//        return self;
//    }
}

