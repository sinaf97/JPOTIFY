package UI;

import Logic.Media;
import Logic.Status;
import Logic.User;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class Right extends JPanel {
    private JpotifyUI jpotifyUI;
    private JPanel onlineFriends;
    private JPanel offlineFriends;


    public Right(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new GridLayout(2,1));
        onlineFriends = new JPanel();
        offlineFriends = new JPanel();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(getList("Online"));
        add(getList("Offline"));


    }

    private void fillOnlineFriends(){
        Constraint c = new Constraint();
        for(User u:jpotifyUI.user.getFriends().getOnlineFriends()) {
            onlineFriends.add(makeFriend(u, true),c.c);
            c.increment();
        }
        for(int i = 0;i<6;i++) {
            onlineFriends.add(makeTrashFriend(), c.c);
            c.increment();
        }
    }
    private void fillOfflineFriends(){
        Constraint c = new Constraint();
        for(User u:jpotifyUI.user.getFriends().getFriendsList().values())
            if(!u.getOnline()) {
                offlineFriends.add(makeFriend(u, false),c.c);
                c.increment();
            }
        for(int i = 0;i<6;i++) {
            offlineFriends.add(makeTrashFriend(), c.c);
            c.increment();
        }
    }

    private JPanel makeFriend(User u,boolean isOnline){
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JLabel name = new JLabel(u.getName());
        JLabel lastPlayed = null;
        try{
            lastPlayed = new JLabel(u.getStatus().getMedia().getName());
        }catch (Exception e){
            lastPlayed = new JLabel("Not Found");
        }
        JLabel status = new JLabel("Playing");
        if(u.getStatus().getStatus())
            status.setText("Playing");
        else
            status.setText("Last played at " + u.getStatus().getTime().toLocalDate().toString());
        if(isOnline) {
            name.setForeground(new Color(30, 215, 96));
            lastPlayed.setForeground(Color.white);
            status.setForeground(Color.white);
        }
        else{
            name.setForeground(new Color(209,209,209));
            lastPlayed.setForeground(Color.GRAY);
            status.setForeground(Color.GRAY);
        }
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 12));
        lastPlayed.setFont(new Font(name.getFont().getName(), Font.PLAIN, 10));
        status.setFont(new Font(name.getFont().getName(), Font.PLAIN, 9));
        name.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        lastPlayed.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        status.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        Constraint c = new Constraint();
        temp.add(name,c.c);
        c.increment();
        temp.add(lastPlayed,c.c);
        c.increment();
        temp.add(status,c.c);
        JLabel trash = new JLabel("&&&&&&&");
        trash.setForeground(new Color(60,60,60));
        temp.add(trash);
        temp.setBackground(new Color(60,60,60));
        temp.setBorder(BorderFactory.createLineBorder(Color.white,2));
        return temp;
    }

    private JPanel makeTrashFriend(){
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JLabel name = new JLabel("s");
        JLabel lastPlayed = new JLabel("s");
        JLabel status = new JLabel("s");
        name.setForeground(new Color(60,60,60));
        lastPlayed.setForeground(new Color(60,60,60));
        status.setForeground(new Color(60,60,60));
        Constraint c = new Constraint();
        temp.add(name,c.c);
        c.increment();
        temp.add(lastPlayed,c.c);
        c.increment();
        temp.add(status,c.c);
        JLabel trash = new JLabel("");
        temp.add(trash);
        temp.setBackground(new Color(60,60,60));
        return temp;
    }

    private JPanel getList(String mode){
        JPanel ListFriends = new JPanel();
        ListFriends.setLayout(new GridBagLayout());
        JPanel ListJpanel = new JPanel();
        ListJpanel.setLayout(new BorderLayout());
        JLabel online;
        if(mode.equals("Online"))
            online = new JLabel("Online Friends");
        else
            online = new JLabel("Offline Friends");
        online.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        online.setBackground(Color.WHITE);
        ListJpanel.add(online,BorderLayout.NORTH);
        if(mode.equals("Online")) {
            fillOnlineFriends();
            ListJpanel.add(new JScrollPane(onlineFriends),BorderLayout.CENTER);
        }
        else {
            fillOfflineFriends();
            ListJpanel.add(new JScrollPane(offlineFriends),BorderLayout.CENTER);
        }
        ListFriends.setBackground(new Color(60,60,60));
        return ListFriends;
    }


    private class Constraint{
        GridBagConstraints c;


        public Constraint(){
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        public void increment(){
            c.gridy++;
        }

    }

}
