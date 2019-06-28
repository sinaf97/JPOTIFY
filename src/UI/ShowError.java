package UI;

import javax.swing.*;
import java.awt.*;

public class ShowError extends JFrame {

    public ShowError(String msg){
        super("Error");
        setSize(200,100);
        showMsg(msg);
    }


    private void showMsg(String msg){
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        JLabel info = new JLabel(msg);
        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> setVisible(false));
        main.add(info);
        main.add(ok,BorderLayout.SOUTH);
        add(main);
        setVisible(true);
    }
}
