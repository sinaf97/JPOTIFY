package UI;

import javax.swing.*;
import java.awt.*;

public class AddFriendUI extends JFrame {


    public AddFriendUI(){
    super("Add Friend");
    initAddFriend();
    setSize(new Dimension(200,100));
    }

    public void initAddFriend(){
        JPanel container = new JPanel();
        JLabel username = new JLabel("Friend Username: ");
        JTextField input = new JTextField();
        input.setColumns(15);
        JButton add = new JButton("Add Friend");
        add.addActionListener(e ->{
            /*
            code for server to add friend
             */
            setVisible(false);
        });
        container.setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.add(username);
        main.add(input);
        container.add(main);
        container.add(add,BorderLayout.SOUTH);
        add(container);
        setVisible(true);
    }
}
