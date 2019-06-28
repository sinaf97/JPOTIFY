package UI.centerElements;

import Logic.Media;
import Logic.MediaList;
import Logic.SongSerial;
import Logic.userCommands.Upload_Client;
import UI.JpotifyUI;
import UI.ShowError;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SongsUI extends JPanel{
    private JpotifyUI jpotifyUI;

    public SongsUI(JpotifyUI jpotifyUI) {
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        int j = 0;
        for(Media song:jpotifyUI.getUser().getLibrary().getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,j));
            j++;

        }
        jpotifyUI.getMain().updateUI();


    }

    public SongsUI(JpotifyUI jpotifyUI,MediaList playlist){
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        int j = 0;
        for(Media song:playlist.getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,playlist,j));
            j++;

        }
        jpotifyUI.getMain().updateUI();


    }

    public SongsUI(JpotifyUI jpotifyUI,MediaList playlist,String mode){
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        int j = 0;
        for(Media song:playlist.getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,playlist,j,"Shared Playlist"));
            j++;

        }
        jpotifyUI.getMain().updateUI();


    }

    public SongsUI(JpotifyUI jpotifyUI, ArrayList<Media> entry) {
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        int j = 0;
        for(Media song:entry) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,j));
            j++;

        }
        jpotifyUI.getMain().updateUI();


    }

    private class Song extends JPanel{

        public Song(Media song,int j) {
            super();
            JButton play = new JButton("Play");
            play.addActionListener(e -> {
                try {
                    jpotifyUI.getUser().getPlayer().setPlayerPlaylist(jpotifyUI.getUser().getLibrary().getAllSongsInPlaylist());
                    jpotifyUI.getUser().getPlayer().changeSong(jpotifyUI.getUser().getLibrary().getAllSongsInPlaylist(), j);
                } catch (Exception e1) {
                    new ShowError("Playing "+song.getName()+" failed.");
                }
            });

            JButton favorite;
            if (song.getFavorite())
                favorite = new JButton("Unlike");
            else
                favorite = new JButton("Like");
            favorite.addActionListener(e -> {
                if (song.getFavorite()) {
                    song.setFavorite(false);
                    favorite.setText("Like");
                    jpotifyUI.getUser().getLibrary().getPlaylists().get("Favorite  Songs").removeSong(song);

                } else {
                    song.setFavorite(true);
                    favorite.setText("Unlike");
                    jpotifyUI.getUser().getLibrary().getPlaylists().get("Favorite  Songs").addSong(song);

                }
                jpotifyUI.getMain().updateUI();
            });


            JButton addToPlaylist = new JButton("+");
            addToPlaylist.addActionListener(e -> addSongToPlaylist(song));
            JButton upload = null;
            if(!song.getShared()) {
                upload = new JButton("Share");
                JButton finalUpload = upload;
                upload.addActionListener(e -> {
                    boolean output = false;
                    try {
                        Upload_Client uploadFile = new Upload_Client(jpotifyUI.getUser());
                        output = uploadFile.upload(new File(song.getDir()));
                    }catch (Exception e1){
                        new ShowError("Sharing the song failed.");
                    }
                    if(output) {
                        song.setShared(true);
                        jpotifyUI.getUser().getLibrary().addToMedialist(song,"Shared Playlist");
                        finalUpload.setText("Shared");
                    }
                });
            }
            else{
                upload = new JButton("Shared");
            }


            setLayout(new GridLayout(1,2));
            JPanel container1 = new JPanel();
            container1.add(play);
            container1.add(favorite);
            container1.add(addToPlaylist);
            JPanel container2 = new JPanel();
            container2.setLayout(new GridLayout(3,1));

            JLabel temp = new JLabel(song.getName());
            JLabel temp2 = new JLabel("Artist: "+song.getArtist());
            JLabel temp3 = new JLabel("Album: "+song.getAlbum());
            temp.setFont(new Font("timesnewroman",Font.BOLD,14));
            temp2.setFont(new Font("timesnewroman",Font.PLAIN,12));
            temp3.setFont(new Font("timesnewroman",Font.PLAIN,10));
            container2.add(temp);
            container2.add(temp2);
            container2.add(temp3);
            add(container1);
            add(container2);
            add(upload);
        }


        public Song(Media song,MediaList playlist,int j){
            super();
            setLayout(new GridLayout(1,5));
            JButton play = new JButton("Play");
            play.addActionListener(e -> {
                try {
                    jpotifyUI.getUser().getPlayer().setPlayerPlaylist(playlist);
                    jpotifyUI.getUser().getPlayer().changeSong(playlist,j);
                } catch (Exception e1) {
                    new ShowError("Playing the song failed.");
                }
            });

            JButton favorite;
            if (song.getFavorite())
                favorite = new JButton("Unlike");
            else
                favorite = new JButton("Like");
            favorite.addActionListener(e -> {
                if(song.getFavorite()) {
                    song.setFavorite(false);
                    favorite.setText("Like");
                    jpotifyUI.getUser().getLibrary().getPlaylists().get("Favorite  Songs").removeSong(song);

                }
                else {
                    song.setFavorite(true);
                    favorite.setText("Unlike");
                    jpotifyUI.getUser().getLibrary().getPlaylists().get("Favorite  Songs").addSong(song);

                }
                jpotifyUI.getMain().updateUI();
            });



            JButton removeFromPlaylist = new JButton("-");
            removeFromPlaylist.addActionListener(e -> {
                removeSongFromPlaylist(song,playlist);
                jpotifyUI.getMain().removeAll();
                if(!playlist.getName().equals("Favorite Songs") && !playlist.getName().equals("Shared Playlist")) {
                    JButton deletePlaylist = new JButton("Delete Playlist");
                    deletePlaylist.addActionListener(e12 -> {
                        jpotifyUI.getUser().getLibrary().removePlayList(playlist);
                        jpotifyUI.getMain().removeAll();
                        jpotifyUI.getMain().updateUI();
                        jpotifyUI.getLeft().updatePlaylist();
                    });
                    jpotifyUI.getMain().add(deletePlaylist);
                }
                jpotifyUI.getMain().add(new JSeparator());
                jpotifyUI.getMain().add(new SongsUI(jpotifyUI,playlist));
                jpotifyUI.getMain().updateUI();
            });

            JButton upload = null;
            if(!song.getShared()) {
                upload = new JButton("Share");
                JButton finalUpload = upload;
                upload.addActionListener(e -> {
                    boolean output = false;
                    try {
                        Upload_Client uploadFile = new Upload_Client(jpotifyUI.getUser());
                        output = uploadFile.upload(new File(song.getDir()));
                    }catch (Exception e1){
                        new ShowError("Sharing the song failed.");
                    }
                    if(output) {
                        song.setShared(true);
                        jpotifyUI.getUser().getLibrary().addToMedialist(song,"Shared Playlist");
                        finalUpload.setText("Shared");
                    }
                });
                upload.setText("Shared");
            }
            else{
                upload = new JButton("Shared");
            }


            setLayout(new GridLayout(1,2));
            JPanel container1 = new JPanel();
            container1.add(play);
            container1.add(favorite);
            container1.add(removeFromPlaylist);
            JPanel container2 = new JPanel();
            container2.setLayout(new GridLayout(3,1));
            JLabel temp = new JLabel(song.getName());
            JLabel temp2 = new JLabel("Artist: "+song.getArtist());
            JLabel temp3 = new JLabel("Album: "+song.getAlbum());
            temp.setFont(new Font("timesnewroman",Font.BOLD,14));
            temp2.setFont(new Font("timesnewroman",Font.PLAIN,12));
            temp3.setFont(new Font("timesnewroman",Font.PLAIN,10));
            container2.add(temp);
            container2.add(temp2);
            container2.add(temp3);
            add(container1);
            add(container2);

        }

        public Song(Media song,MediaList playlist,int j,String mode){
            super();
            setLayout(new GridLayout(1,5));
            JButton play = new JButton("Play");
            play.addActionListener(e -> {
                try {
                    jpotifyUI.getUser().getPlayer().setPlayerPlaylist(playlist);
                    /*
                    code here to fetch the song
                     */
                    /*
                    implement a code in which you play a single song
                     */
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            JButton download = new JButton("Download");
            play.addActionListener(e -> {
                try {
                    /*
                    code here to fetch the song
                     */

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });


            setLayout(new GridLayout(1,2));
            JPanel container1 = new JPanel();
            container1.add(play);
            container1.add(download);
            JPanel container2 = new JPanel();
            container2.setLayout(new GridLayout(3,1));
            JLabel temp = new JLabel(song.getName());
            JLabel temp2 = new JLabel("Artist: "+song.getArtist());
            JLabel temp3 = new JLabel("Album: "+song.getAlbum());
            temp.setFont(new Font("timesnewroman",Font.BOLD,14));
            temp2.setFont(new Font("timesnewroman",Font.PLAIN,12));
            temp3.setFont(new Font("timesnewroman",Font.PLAIN,10));
            container2.add(temp);
            container2.add(temp2);
            container2.add(temp3);
            add(container1);
            add(container2);

        }

        public void addSongToPlaylist(Media song){
            JFrame selectPlaylist = new JFrame("Select Playlist");
            Object[] names = jpotifyUI.getUser().getLibrary().getPlaylists().keySet().toArray();
            selectPlaylist.setSize(new Dimension(300,names.length*20+100));
            JPanel container = new JPanel();
            JList playlists = new JList(names);
            JButton add = new JButton("Add");
            add.addActionListener(e -> {
                jpotifyUI.getUser().getLibrary().addToMedialist(song,(String) playlists.getSelectedValue());
                selectPlaylist.setVisible(false);
            });
            container.setLayout(new BoxLayout(container,3));
            container.add(playlists);
            container.add(add);
            selectPlaylist.add(container);
            selectPlaylist.setVisible(true);
        }

        public void removeSongFromPlaylist(Media song,MediaList playlist){
            jpotifyUI.getUser().getLibrary().removeFromMediaList(song,playlist);
            jpotifyUI.getMain().updateUI();

        }

    }

}
