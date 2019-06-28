package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;


import Logic.userCommands.Close_Client;
import UI.centerElements.SongsUI;


public class Header extends JPanel {
    private JpotifyUI jpotifyUI;

    public Header(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);
        JLabel username = new JLabel("User: " + jpotifyUI.user.getName() );
        username.setForeground(Color.white);
        username.setBorder(new EmptyBorder(10,10,10,10));

        JComboBox cbo = new JComboBox();
        cbo.setEditable(true);
        cbo.setUI(new javax.swing.plaf.metal.MetalComboBoxUI(){
            public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
                super.layoutComboBox(parent, manager);
                arrowButton.setBounds(0,0,0,0);
                if (editor != null) {
                    Rectangle r = rectangleForCurrentValue();
                    editor.setBounds(r);
                }
            }
        });

        cbo.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        cbo.getEditor().getEditorComponent().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(cbo.getEditor().getItem().equals(""))
                    cbo.hidePopup();
                else{
                    String temp = (String) cbo.getEditor().getItem();
                    cbo.removeAllItems();
                    cbo.addItem(temp);
                    for(String m:jpotifyUI.user.getLibrary().searchSong(temp))
                        cbo.addItem(m);
                    cbo.hidePopup();
                    cbo.showPopup();
                }
            }
        });

        JButton searchButton = new JButton("Search");



        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbo.getEditor().getItem().equals(""))
                    return;
                System.out.println("in");
                jpotifyUI.getMain().removeAll();
                jpotifyUI.getMain().add(new SongsUI(jpotifyUI,jpotifyUI.getUser().getLibrary().getSearchSongResult((String)cbo.getEditor().getItem())));
                jpotifyUI.getMain().updateUI();
            }
        });



        add(username,BorderLayout.WEST);
        JPanel searchArea = new JPanel();
        searchArea.setBackground(Color.GRAY);
        searchArea.setLayout(new FlowLayout());
        searchArea.add(searchButton);
        searchArea.add(cbo);
        add(searchArea,BorderLayout.EAST);
        JButton addFriend = new JButton("Add Friend");
        addFriend.addActionListener(e -> new AddFriendUI(jpotifyUI.user));
        JButton logout = new JButton("Log out");
        addFriend.addActionListener(e -> {
            Close_Client close = new Close_Client(jpotifyUI.getUser());
            try {
                jpotifyUI.user.getStatus().setStatus(false);
                close.closeAction();
            }catch (Exception e1){}
            jpotifyUI.setVisible(false);
            new LoginPage();
        });
        JPanel buttonContainer = new JPanel();
        buttonContainer.add(logout);
        buttonContainer.add(addFriend);
        buttonContainer.setBackground(Color.GRAY);
        add(buttonContainer);



    }

}
