package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

// Attribution[1]: The try-catch was modified with respect to the main function in "JsonSerializationDemo"
public class Main {
    public static void main(String[] args) {
        JFrame errorWindow = new JFrame();
        try {
            MainWindow mainWindow = new MainWindow();
            errorWindow.add(mainWindow);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(errorWindow, "File not found");
        }

    }
}
