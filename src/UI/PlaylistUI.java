package UI;

import Logic.ExistException;
import Logic.MediaList;
import UI.centerElements.SongsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlaylistUI extends JPanel {
    private JpotifyUI jpotifyUI;
    private JPanel playlist;

    public PlaylistUI(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        setBackground(Color.black);

        JLabel title = Left.makeTitle("Playlists");
        playlist = makePlaylistSection();
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(playlist,BorderLayout.NORTH);
        add(title,BorderLayout.NORTH);
        container.setBackground(Color.black);
        add(container,BorderLayout.CENTER);
    }



    private JPanel makePlaylistSection() {
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JButton add = makeAddButton();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        temp.add(add,c);
        int i = 1;
        for(MediaList playlist:jpotifyUI.user.getLibrary().getPlaylists().values()){
                JButton playListButton = makeCustomButton(playlist);
                c.gridy = i++;
                temp.add(playListButton,c);
        }
        temp.setBackground(Color.black);
        return temp;
    }

    private JButton makeAddButton() {
        JButton temp = new JButton("Add Playlist");
        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame getInfo = new JFrame("New Playlist");
                getInfo.setSize(300,100);
                JLabel name = new JLabel("Playlist Name: ");
                JTextField input = new JTextField();
                JButton create = new JButton("Create");
                JPanel container = new JPanel();
                container.setLayout(new GridLayout(2,2));
                container.add(name);
                container.add(input);
                create.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!input.getText().equals("")) {
                                jpotifyUI.getUser().getLibrary().addPlayList(input.getText());
                                getInfo.setVisible(false);
                            } else {
                                JFrame error = new JFrame("ERROR");
                                error.setSize(300,100);
                                JPanel container = new JPanel();
                                JLabel msg = new JLabel("Please enter a name!");
                                JButton ok = new JButton("Ok");
                                ok.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        error.setVisible(false);
                                    }
                                });
                                container.add(msg);
                                container.add(ok);
                                error.add(container);
                                error.setVisible(true);

                            }
                        }catch (ExistException ex){
                            JFrame error = new JFrame("ERROR");
                            error.setSize(300,100);
                            JPanel container = new JPanel();
                            JLabel msg = new JLabel("Playlist already exists.");
                            JButton ok = new JButton("Ok");
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    error.setVisible(false);
                                }
                            });
                            container.add(msg);
                            container.add(ok);
                            error.add(container);
                            error.setVisible(true);
                        }
                        jpotifyUI.getLeft().updatePlaylist();

                    }
                });
                container.add(create);
                getInfo.add(container);
                getInfo.setVisible(true);
            }});
        return temp;
    }

    private JButton makeCustomButton(MediaList playlist){
        if(playlist.getName().equals("Shared Playlists"))
            return makeSharedPlaylistButton(playlist);
        JButton temp = new JButton(playlist.getName());
        temp.addActionListener(e -> {
            jpotifyUI.getMain().removeAll();
            if(!playlist.getName().equals("Favorite Songs") && !playlist.getName().equals("Shared Playlists")) {
                JButton deletePlaylist = new JButton("Delete Playlist");
                deletePlaylist.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jpotifyUI.getUser().getLibrary().removePlayList(playlist);
                        jpotifyUI.getMain().removeAll();
                        jpotifyUI.getMain().updateUI();
                        jpotifyUI.getLeft().updatePlaylist();
                    }
                });
                jpotifyUI.getMain().add(deletePlaylist);
            }
            jpotifyUI.getMain().add(new JSeparator());
            jpotifyUI.getMain().setLayout(new FlowLayout());
            jpotifyUI.getMain().add(new SongsUI(jpotifyUI,playlist));
            jpotifyUI.getMain().updateUI();
        });
        return temp;
    }

    private JButton makeSharedPlaylistButton(MediaList playlist){
        JButton share = new JButton("Shared Playlist");
        share.addActionListener(e ->{
            jpotifyUI.getMain().removeAll();
            jpotifyUI.getMain().setLayout(new FlowLayout());
            jpotifyUI.getMain().add(new SongsUI(jpotifyUI,playlist,"Shared Playlists"));
            jpotifyUI.getMain().updateUI();
        });
        return share;
    }

}
