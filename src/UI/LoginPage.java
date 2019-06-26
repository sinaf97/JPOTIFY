package UI;

import Logic.User;
import Logic.userCommands.CreateAccount_Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class LoginPage extends JFrame {
    private JPanel main;

    public LoginPage(){
        super("Login to JPotify");
        setSize(new Dimension(500,200));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        main = new JPanel();
        makeLoginPage();
        add(main);
        setVisible(true);

    }

    private void makeLoginPage(){
        JLabel username = new JLabel("Username: ");
        JTextField input = new JTextField();
        input.setColumns(20);
        JPanel container1 = new JPanel();
        container1.add(username);
        container1.add(input);
        main.add(container1);
        JButton login = new JButton("Log in");
        JButton signup = new JButton("Sign up");

        login.addActionListener(e -> {
            /*
            codes for logging in with server auth
             */
        });
        signup.addActionListener(e -> {
            makeSignUpPage();

        });
        JPanel container = new JPanel();
        container.add(login,BorderLayout.SOUTH);
        container.add(signup,BorderLayout.SOUTH);
        main.add(container,BorderLayout.SOUTH);
    }

    private void makeSignUpPage(){
        main.removeAll();
        JLabel username = new JLabel("Username: ");
        JTextField input = new JTextField();
        input.setColumns(20);
        JLabel name = new JLabel("Name: ");
        JTextField inputName = new JTextField();
        inputName.setColumns(20);
        JPanel container1 = new JPanel();
        container1.setLayout(new GridLayout(2,2));
        container1.add(username);
        container1.add(input);
        container1.add(name);
        container1.add(inputName);
        main.add(container1);
        JButton signup = new JButton("Create account");
        signup.addActionListener(e->{
            /*
            codes for creating account with server auth
             */
            System.out.println("button clicked");
            try {
                User attempt = new User(username.getText(), inputName.getText());
                boolean temp = new CreateAccount_Client(attempt, "localhost", 44444).confirm();
                if (temp) {
                    attempt.setUi(new JpotifyUI(attempt));
                    setVisible(false);
                }
                else{
                    showError("Username already exists.");
                }

            }catch (Exception e1){
                System.out.println(e);
            }
        });
        JPanel container = new JPanel();
        container.add(signup,BorderLayout.SOUTH);
        main.add(container,BorderLayout.SOUTH);
        main.updateUI();

    }

    public void showError(String msg){

    }
}
