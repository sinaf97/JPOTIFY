package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class LoginPage extends JFrame {

    public LoginPage(){
        super("Login to JPotify");
        setSize(new Dimension(500,500));
        setLayout(new BorderLayout());
        JLabel username = new JLabel("Username: ");
        JTextField input = new JTextField();
        add(username);
        add(input);
        JButton login = new JButton("Log in");
        JButton signup = new JButton("Sign up");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
