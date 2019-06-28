package UI;

import Logic.User;
import Logic.userCommands.AddFriend_Client;

import javax.swing.*;
import java.awt.*;

public class AddFriendUI extends JFrame {
        User user;

    public AddFriendUI(User user){
    super("Add Friend");
    this.user = user;
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
            try{
                AddFriend_Client addFriend = new AddFriend_Client(user,input.getText());
                User newFriend = addFriend.makeFriend();
                user.getFriends().addFriend(newFriend);
                user.getUi().getRight().updateFriends();
                setVisible(false);
            }catch (Exception e1){
                new ShowError("Username not found.");
            }
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
