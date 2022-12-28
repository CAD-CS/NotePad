package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.PadManager;
import model.PadManagerTest;

public class MainWindow {
    
    private JFrame mainWindow; 
    private JMenuBar menuBar;
    private TabOfPads tabs;
    private SavedFiles savedFiles;

    public MainWindow() {
        mainWindow = new JFrame();
        menuBar = new JMenuBar();
        initialize();
    }

    private void initialize() {
        createMenuItems();
        mainWindow.setVisible(true);
        mainWindow.setSize(500,500);
    }

    private void createMenuItems() {
        JMenuItem savePad = new JMenuItem("Save Pad");
        JMenuItem loadPad = new JMenuItem("Load Pad");
        JMenu menu = new JMenu("File");

        savePad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                PadManager.getInstance().saveSelectedPad();
                JOptionPane.showMessageDialog(mainWindow, "Saved:" + PadManager.getInstance().getSelectedPad().getPadTitle());                
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(mainWindow, "File not found");
                }
            }
        });

        loadPad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ea) {
                try {
                String padName = JOptionPane.showInputDialog(mainWindow, "Load file...");
                PadManager.getInstance().loadPad(padName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } 
            }
        });
        menu.add(savePad);
        menu.add(loadPad);
        menuBar.add(menu);
        mainWindow.setJMenuBar(menuBar);
    }


}
