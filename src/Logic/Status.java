package Logic;


import java.time.LocalDateTime;

public class Status{
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
