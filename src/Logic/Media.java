package Logic;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * songs added to the user are represented
 * by this class which holds all the information
 * needed for the songs
 */

public class Media implements Serializable{
    private String dir;
    private String name;
    private String artist;
    private String album;
    private int length;
    private ImageIcon artWork;
    private Boolean favorite;
    private static ImageIcon defaultArtWork;
    private boolean shared = false;

    static {
        try {
            BufferedImage bImage = ImageIO.read(new File("/Users/sinafarahani/Desktop/this-term/AP/project/src/icons/artwork.png"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos );
            byte [] data = bos.toByteArray();
            defaultArtWork = new ImageIcon(scale(data,50,50,new Color(0,0,0)));
        } catch (Exception e) {
        }
    }


    //constructor
    public Media(String dir) throws Exception {
        this.dir = dir;
        File song = new File(dir);
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(song));
        long length = song.length();
        input.skip(length-128);
        byte[] last128 = new byte[128];

        input.read(last128);
        String info = new String(last128);
        String tag = info.substring(0, 3);
        if (tag.equals("TAG")){
            this.name = info.substring(3, 33);
            this.artist = info.substring(33, 63);
            this.album = info.substring(63, 93);
        }
        input.close();
        Mp3File mp3file = new Mp3File(dir);
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            if(id3v2Tag.getAlbumImage() != null){
                artWork = new ImageIcon(scale(id3v2Tag.getAlbumImage(),50,50,new Color(0,0,0)));
            }
            else
                artWork = defaultArtWork;

        }
        this.length = (int) mp3file.getLengthInSeconds();
        checkFields(song);
        favorite = false;

}

/*
* ************************************************
* ************************************************
* *****loading default artwork in static mode*****
* ************************************************
* ************************************************
*
 */
    static{
        File temp = new File("icons/artwork.png");
        try {
        } catch (Exception e){}
    }

    /*
     * ************************************************
     * ************************************************
     * ****************getters*************************
     * ************************************************
     * ************************************************
     *
     */

    public String getDir() {
        return dir;
    }

    public String getName(){
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }
    public String[] getInfo(){
        return new String[]{name, artist, album};
    }

    public boolean getShared(){
        return shared;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public int getLength() {
        return length;
    }

    public ImageIcon getArtWork() {
        return artWork;
    }


    //check for fields and set default values to null ones
    public void checkFields(File song){
        if(name == null)
            name = song.getName().split("\\.")[0];
        if(artist == null)
            artist = "Various Artists";
        if(album == null)
            album = "Unknown";

    }


    public void setShared(boolean shared){
        this.shared = shared;
    }


    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }


    /**
     * returns an image by the size we want(it will scale the image)
     * @param fileData
     * @param width
     * @param height
     * @param back
     * @return
     * @throws Exception
     */
    public static byte[] scale(byte[] fileData, int width, int height,Color back) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, back, null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new Exception();
        }
    }


}
