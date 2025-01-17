package Logic;
import UI.JpotifyUI;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * Class User is the identity of our client
 */

public class User implements Serializable {
    private String username;
    private String name;
    private Friend friends;
    private Library library;
    private Status status;
    private Boolean online;
    private JpotifyUI ui;
    private Player player;

    public User(String username,String name) throws FileNotFoundException, InterruptedException, JavaLayerException { //Constructor
        this.username = username;
        this.name = name;
        friends = new Friend(this);
        library = new Library(this);
        online = true;
        player = Player.getInstance(this);

    }

    public String getName() {
        return name;
    }

    public Boolean getOnline() {
        return online;
    }

    public Friend getFriends() {
        return friends;
    }

    public Library getLibrary() {
        return library;
    }

    public Status getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public JpotifyUI getUi() {
        return ui;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public void setUi(JpotifyUI ui) {
        this.ui = ui;
    }

    public Player getPlayer() {
        return player;
    }
}
