package UI.centerElements;

import Logic.Album;
import Logic.MediaList;
import Logic.userCommands.SharedPlaylist_GetList_Client;
import Logic.userCommands.SharedPlaylist_GetSongsName_Client;
import UI.JpotifyUI;
import UI.ShowError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class SharedPlaylistUI extends JPanel {

    private JpotifyUI jpotifyUI;

    public SharedPlaylistUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        int i = 1;
        ArrayList<String> shared = new ArrayList<>();
        SharedPlaylist_GetList_Client getList = new SharedPlaylist_GetList_Client(jpotifyUI.getUser());
        try {
            shared = getList.getListAction();
        }catch (Exception e){
            new ShowError("Getting shared plalist list failed.");
        }
        shared.add(0,"My Shared Playlist");
        for (String share: shared) {
            setLayout(new GridLayout(i++,1));
            add(new SharedplaylistUI(share));

        }
        jpotifyUI.getMain().updateUI();


    }

    private class SharedplaylistUI extends JPanel {

        public SharedplaylistUI(String Sharedplaylist){
            super();
            setLayout(new GridLayout(1,3));
            JButton browse = new JButton("Browse Songs");
            browse.addActionListener(e -> {
                jpotifyUI.getMain().removeAll();
                jpotifyUI.getMain().setLayout(new FlowLayout());
                if(Sharedplaylist.equals("My Shared Playlist"))
                    jpotifyUI.getMain().add(new SongsUI(jpotifyUI,jpotifyUI.getUser().getLibrary().getPlaylists().get("Shared Playlist"),"Shared Playlist"));
                else{
                    SharedPlaylist_GetSongsName_Client getSongs = new SharedPlaylist_GetSongsName_Client(jpotifyUI.getUser().getFriends().getFriendsList().get(Sharedplaylist));
                    ArrayList<String> songs = new ArrayList<>();
                    try{
                        songs = getSongs.getSongsNameAction("Sharedplaylist");
                    }catch (Exception e1){
                        new ShowError("Getting songs of "+Sharedplaylist+"'s shared playlisst failed.");
                    }
                    jpotifyUI.getMain().add(new SharedSongsUI(jpotifyUI,songs,Sharedplaylist,"Shared Playlist"));
                }

            });
            add(browse);
            add(new JLabel(Sharedplaylist));
        }
    }
}
