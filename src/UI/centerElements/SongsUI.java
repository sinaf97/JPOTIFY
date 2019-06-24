package UI.centerElements;

import Logic.Media;
import Logic.MediaList;
import UI.JpotifyUI;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SongsUI extends JPanel{
    private JpotifyUI jpotifyUI;

    public SongsUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        for(Media song:jpotifyUI.getUser().getLibrary().getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song));

        }
        jpotifyUI.getMain().updateUI();


    }

    public SongsUI(JpotifyUI jpotifyUI,MediaList playlist){
        super();
        this.jpotifyUI = jpotifyUI;
        int i = 1;
        for(Media song:playlist.getSongs()) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song,playlist));

        }
        jpotifyUI.getMain().updateUI();


    }

    public SongsUI(JpotifyUI jpotifyUI, ArrayList<Media> entry){
        super();
        this.jpotifyUI = jpotifyUI;
//        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        int i = 1;
        for(Media song:entry) {
            setLayout(new GridLayout(i++,1));
            add(new Song(song));

        }
        jpotifyUI.getMain().updateUI();


    }

    private class Song extends JPanel{

        public Song(Media song){
            super();
            setLayout(new GridLayout(1,5));
            JButton play = new JButton("Play");
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        jpotifyUI.getUser().getPlayer().changeSong(song.getDir());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            add(play);
            JButton favorite;
            if (song.getFavorite())
                favorite = new JButton("Unlike");
            else
                favorite = new JButton("Like");
            favorite.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                }

            });
            add(favorite);


            JButton addToPlaylist = new JButton("+");
            addToPlaylist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addSongToPlaylist(song);
                }});
            add(addToPlaylist);



            add(new JLabel(song.getName()));
            add(new JLabel(song.getArtist()));
            add(new JLabel(song.getAlbum()));
        }


        public Song(Media song,MediaList playlist){
            super();
            setLayout(new GridLayout(1,5));
            JButton play = new JButton("Play");
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        jpotifyUI.getUser().getPlayer().changeSong(song.getDir());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            add(play);
            JButton favorite;
            if (song.getFavorite())
                favorite = new JButton("Unlike");
            else
                favorite = new JButton("Like");
            favorite.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                }

            });
            add(favorite);


            JButton removeFromPlaylist = new JButton("-");
            removeFromPlaylist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeSongFromPlaylist(song,playlist);
                    jpotifyUI.getMain().removeAll();
                    if(!playlist.getName().equals("Favorite Songs") && !playlist.getName().equals("Shared Playlist")) {
                        JButton deletePlaylist = new JButton("Delete Playlist");
                        deletePlaylist.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                            }
                        });
                        jpotifyUI.getMain().add(deletePlaylist);
                    }
                    jpotifyUI.getMain().add(new JSeparator());
                    jpotifyUI.getMain().add(new SongsUI(jpotifyUI,playlist));
                    jpotifyUI.getMain().updateUI();
                }});
            add(removeFromPlaylist);



            add(new JLabel(song.getName()));
            add(new JLabel(song.getArtist()));
            add(new JLabel(song.getAlbum()));
        }

        public void addSongToPlaylist(Media song){
            JFrame selectPlaylist = new JFrame("Select Playlist");
            Object[] names = jpotifyUI.getUser().getLibrary().getPlaylists().keySet().toArray();
            selectPlaylist.setSize(new Dimension(300,names.length*20+100));
            JPanel container = new JPanel();
            JList playlists = new JList(names);
            JButton add = new JButton("Add");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jpotifyUI.getUser().getLibrary().addToMedialist(song,(String) playlists.getSelectedValue());
                    selectPlaylist.setVisible(false);
                }
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
