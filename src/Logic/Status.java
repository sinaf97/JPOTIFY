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

}
