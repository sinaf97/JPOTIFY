package Logic;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * status of a user to determine the last played situation of the user
 */

public class Status implements Serializable {
    private String songName;
    private Boolean status;
    private LocalDateTime time;

    public Status(String songName){
        this.songName = songName;
        status = true;
        time = LocalDateTime.now();
    }
    public Status(String songName,boolean status){
        this.songName = songName;
        this.status = status;
        time = LocalDateTime.now();
    }

    public Boolean getStatus() {
        return status;
    }

    public String getSongName() {
        return songName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
