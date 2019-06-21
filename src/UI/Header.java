package UI;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

import Logic.Media;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Header extends JPanel {
    private JpotifyUI jpotifyUI;

    public Header(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new BorderLayout());
        JLabel username = new JLabel("username");
        JTextField searchBox = new JTextField();
        JLabel search = new JLabel("Search: ");


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
                    System.out.println(temp);
                    for(String m:jpotifyUI.user.getLibrary().searchSong(temp))
                        cbo.addItem(m);
                    cbo.hidePopup();
                    cbo.showPopup();
                }
            }
        });

        cbo.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                    here is when you choose a song of your search
                    heres the code you write to tell it what it should do
                 */
                System.out.println(cbo.getSelectedItem());
            }
        });



        add(username,BorderLayout.WEST);
        JPanel searchArea = new JPanel();
        searchArea.setLayout(new BorderLayout());
        searchArea.add(search,BorderLayout.WEST);
        searchArea.add(cbo,BorderLayout.CENTER);
        add(searchArea,BorderLayout.CENTER);



    }

}
