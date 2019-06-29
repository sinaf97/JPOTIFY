package UI;

import javax.swing.*;
import java.awt.*;

public class ShowError extends JFrame {

    public ShowError(String msg){
        super("Error");
        setSize(300,120);
        showMsg(msg);
    }


    private void showMsg(String msg){
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout(FlowLayout.CENTER,5,12));
        JLabel info = new JLabel(msg);
        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> setVisible(false));
        main.add(info);
        main.add(ok);
        add(main);
        setVisible(true);
    }
}
