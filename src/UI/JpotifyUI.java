package UI;

import Logic.User;

import javax.swing.*;
import java.awt.*;

public class JpotifyUI extends JFrame {
    User user;
    private Header header;
    private Left left;
    private Right right;
    private Center main;
    private Footer footer;

    public JpotifyUI(User user){
        super();
        this.user = user;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        header = new Header(this);
        left = new Left(this);
        right = new Right(this);
        main = new Center(this);
        footer= new Footer(this);
        add(header,BorderLayout.NORTH);
        add(left,BorderLayout.WEST);
        add(main,BorderLayout.CENTER);
        add(right,BorderLayout.EAST);
        add(footer,BorderLayout.SOUTH);
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
