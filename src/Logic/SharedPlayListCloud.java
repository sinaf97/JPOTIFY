package Logic;

import java.io.*;
import java.util.ArrayList;

/**
 * Just for add or remove a song from serve System
 */
public class SharedPlayListCloud {

    private User user;
    private ArrayList<Media> songs;

    public SharedPlayListCloud(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addSong(Media media) {
        if (!songs.contains(media)) {
            this.songs.add(media);
            storeMedia(media);
        }
    }

    public void removeMedia(Media media) {
        if (songs.contains(media)) {
            this.songs.remove(media);
            deleteFromStorage(media);
        }
    }

    private void storeMedia(Media media) {

        String sourceFile = media.getDir();
        String destFile = media.getName();
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int noOfBytes = 0;

            // read bytes from source file and write to destination file
            while ((noOfBytes = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, noOfBytes);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while copying file " + ioe.getMessage());
        }
        finally {
            // close the streams using close method
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    private void deleteFromStorage(Media media) {
        File file = new File(media.getName());

        if(file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }
    }
}
