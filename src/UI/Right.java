package UI;


import Logic.User;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Right extends JPanel {
    private JpotifyUI jpotifyUI;
    private JPanel onlineFriends;
    private JPanel offlineFriends;


    public Right(JpotifyUI jpotifyUI){
        super();
        this.jpotifyUI = jpotifyUI;
        setLayout(new GridLayout(2,1));
        onlineFriends = getList("Online");
        offlineFriends = getList("Offline");
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(onlineFriends);
        add(offlineFriends);


    }

    private JPanel getList(String mode){
        JPanel listFriends = new JPanel();
        listFriends.setLayout(new GridBagLayout());
        JPanel listJpanel = new JPanel();
        listJpanel.setLayout(new BorderLayout());
        JLabel online;
        if(mode.equals("Online"))
            online = new JLabel("Online Friends");
        else
            online = new JLabel("Offline Friends");
        online.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        online.setBackground(Color.WHITE);
        listJpanel.add(online,BorderLayout.NORTH);
        if(mode.equals("Online"))
            fillOnlineFriends(listFriends);
        else
            fillOfflineFriends(listFriends);
        listJpanel.add(new JScrollPane(listFriends),BorderLayout.CENTER);
        listFriends.setBackground(new Color(0,0,0));
        return listJpanel;
    }



    private void fillOnlineFriends(JPanel listFriends){
        Constraint c = new Constraint();
        for(User u:jpotifyUI.user.getFriends().getOnlineFriends()) {
            listFriends.add(makeFriend(u, true),c.c);
            c.increment();
        }
        for(int i = 0;i<7;i++) {
            listFriends.add(makeTrashFriend(), c.c);
            c.increment();
        }
    }
    private void fillOfflineFriends(JPanel listFriends){
        Constraint c = new Constraint();
        for(User u:jpotifyUI.user.getFriends().getFriendsList().values())
            if(!u.getOnline()) {
                listFriends.add(makeFriend(u, false),c.c);
                c.increment();
            }
        for(int i = 0;i<7;i++) {
            listFriends.add(makeTrashFriend(), c.c);
            c.increment();
        }
    }

    private JPanel makeFriend(User u,boolean isOnline){
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JLabel name = new JLabel(u.getName());
        JLabel lastPlayed = null;
        try{
            lastPlayed = new JLabel(u.getStatus().getSongName());
        }catch (Exception e){
            lastPlayed = new JLabel("Not Found");
        }
        JLabel status = new JLabel("Playing");
        if(u.getStatus().getStatus())
            status.setText("Playing");
        else {
            LocalTime timeTemp =  LocalTime.ofSecondOfDay(LocalDateTime.now().getSecond() - u.getStatus().getTime().getSecond());
            String lastTime = timeTemp.getHour()>0?timeTemp.getHour()+"h":timeTemp.getMinute()+"min";
            status.setText("Last Played " + lastTime+ " ago");
        }
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
        trash.setForeground(new Color(0,0,0));
        temp.add(trash);
        temp.setBackground(new Color(0,0,0));
        temp.setBorder(BorderFactory.createLineBorder(Color.white,2));
        return temp;
    }

    private JPanel makeTrashFriend(){
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        JLabel name = new JLabel("s");
        JLabel lastPlayed = new JLabel("s");
        JLabel status = new JLabel("s");
        name.setForeground(new Color(0,0,0));
        lastPlayed.setForeground(new Color(0,0,0));
        status.setForeground(new Color(0,0,0));
        Constraint c = new Constraint();
        temp.add(name,c.c);
        c.increment();
        temp.add(lastPlayed,c.c);
        c.increment();
        temp.add(status,c.c);
        JLabel trash = new JLabel("");
        temp.add(trash);
        temp.setBackground(new Color(0,0,0));
        return temp;
    }

    public void updateFriends(){
        removeAll();
        onlineFriends = getList("Online");
        offlineFriends = getList("Offline");
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(onlineFriends);
        add(offlineFriends);
        updateUI();
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
