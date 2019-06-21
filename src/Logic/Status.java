package Logic;


import java.time.LocalDateTime;

public class Status{
    private Media media;
    private Boolean status;
    private LocalDateTime time;

    public Status(Media media){
        this.media = media;
        status = true;
        time = LocalDateTime.now();
    }
    public Status(Media media,boolean status){
        this.media = media;
        this.status = status;
        time = LocalDateTime.now();
    }

    public Boolean getStatus() {
        return status;
    }

    public Media getMedia() {
        return media;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
