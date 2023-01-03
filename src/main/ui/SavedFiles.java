package ui;

import javax.swing.*;

import java.awt.*;
import java.io.File;

public class SavedFiles extends JPanel {

    private static final String path = "./data";
    private File file = new File(path);

    public SavedFiles() {
        super(new GridLayout(0,1));
        displayFiles();
    }

    public void displayFiles() {
        File[] listOfFiles = file.listFiles();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String toPrint = listOfFile.getName().substring(0,listOfFile.getName().length() - 5);
                add(new JLabel(toPrint));
            }
        }
    }
}
