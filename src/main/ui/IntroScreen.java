package ui;

import javax.swing.*;
import java.awt.*;

public class IntroScreen extends JFrame {

    public IntroScreen() {
        super("VimPad Splash");
        this.add(new JLabel(new ImageIcon("./data/IntroArt.jpg")));
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        try {
            Thread.sleep(5000 / 2);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this,"Something went wrong");
        }
        setVisible(false);

    }


}
