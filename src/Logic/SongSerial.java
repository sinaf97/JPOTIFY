package Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * a class to transfer songs to and from server
 */

public class SongSerial implements Serializable {
    private String name;
    private byte[] FileInByte;

    public SongSerial(File input){
        try {
            name = input.getName();
            FileInByte = new byte[(int) input.length()];
            FileInputStream fis = new FileInputStream(input);
            fis.read(FileInByte);
            fis.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public byte[] getFileInByte() {
        return FileInByte;
    }
    public File getFile(){
        File temp = null;
        try {
            temp = new File("downloaded/" + name);
            FileOutputStream fout = new FileOutputStream(temp);
            fout.write(FileInByte);
            fout.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return temp;
    }
}
