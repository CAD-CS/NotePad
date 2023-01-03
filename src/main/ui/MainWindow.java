package ui;

import javax.swing.JFrame;

import java.awt.*;

public class MainWindow extends JFrame {

    private MenuOptions menuOptions;

    public MainWindow() {
        super();
        runMainWindow();
    }

    private void runMainWindow() {
        menuOptions = new MenuOptions(new TabOfPads(), this);
        initialize();
    }

    private void initialize() {
        this.setVisible(true);
        this.setSize(500,500);
        this.setJMenuBar(menuOptions);
        this.setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
    }
}