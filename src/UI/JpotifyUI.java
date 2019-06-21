package UI;

import Logic.User;

import javax.swing.*;
import java.awt.*;

public class JpotifyUI extends JFrame {
    User user;
    private Header header = new Header();
    private Left left = new Left();
    private Right right = new Right();
    private Center main = new Center();
    private Footer footer= new Footer();

    public JpotifyUI(User user){
        super();
        this.user = user;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        header = new Header();
        left = new Left();
        right = new Right();
        main = new Center();
        footer= new Footer();
        add(header,BorderLayout.NORTH);
        add(left,BorderLayout.WEST);
        add(main,BorderLayout.CENTER);
        add(right,BorderLayout.EAST);
        add(footer,BorderLayout.SOUTH);
        setSize(500,300);
        setVisible(true);
    }

    public Center getMain() {
        return main;
    }

    public Footer getFooter() {
        return footer;
    }

    public Header getHeader() {
        return header;
    }

    public Left getLeft() {
        return left;
    }

    public Right getRight() {
        return right;
    }
}
