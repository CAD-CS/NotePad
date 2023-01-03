package ui;

import model.Note;
import model.Pad;
import model.PadManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuOptions extends JMenuBar {

    private TabOfPads tabOfPads;
    private MainWindow mainWindow;
    private SavedFiles savedFiles;

    public MenuOptions(TabOfPads tabbedPane, MainWindow mainWindow) {
        super();
        this.tabOfPads = tabbedPane;
        this.mainWindow = mainWindow;
        this.savedFiles = new SavedFiles();
        JScrollPane scrollPane = new JScrollPane(savedFiles);
        createMenuItems();
        mainWindow.add(tabOfPads, BorderLayout.CENTER);
        mainWindow.add(scrollPane, BorderLayout.EAST);
    }

    private void createMenuItems() {
        addFileItems();
        addNewItems();
        addRemoveItems();
        addChangeItems();
    }

    private void addFileItems() {
        JMenuItem savePad = new JMenuItem("Save Pad");
        JMenuItem loadPad = new JMenuItem("Load Pad");
        JMenu file = new JMenu("File...");

        savePad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ea) {
                try {
                    PadManager.getInstance().saveSelectedPad();
                    JOptionPane.showMessageDialog(mainWindow,
                            "Saved:" + PadManager.getInstance().getSelectedPad().getPadTitle());
                    savedFiles.displayFiles();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(mainWindow, "File not found");
                }
            }
        });
        loadPad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ea) {
                String padName = safeInput("Load file...");
                try {
                    PadManager.getInstance().loadPad(padName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Pad p = PadManager.getInstance().getPad(padName);
                tabOfPads.addPad(p);
            }
        });
        file.add(savePad);
        file.add(loadPad);
        this.add(file);
    }

    private void addNewItems() {
        JMenuItem newPad = new JMenuItem("New Pad");
        JMenuItem newNote = new JMenuItem("New Note");
        JMenu newPadNote = new JMenu("New...");
        newPad.addActionListener(ea -> {
            String padName = safeInput("Title:");
            PadManager.getInstance().newPad(padName);
            Pad p = new Pad(padName);
            tabOfPads.addPad(p);
        });

        newNote.addActionListener(ea -> {
            String noteName = safeInput("Title:");
            PadManager.getInstance().newNote(noteName);
            Note n = new Note(noteName);
            tabOfPads.addNote(n);
        });

        newPadNote.add(newPad);
        newPadNote.add(newNote);
        this.add(newPadNote);
    }

    private void addRemoveItems() {
        JMenuItem removePad = new JMenuItem("Remove Pad");
        JMenuItem removeNote = new JMenuItem("Remove Note");
        JMenu remove = new JMenu("Remove...");
        removePad.addActionListener(ea -> {
            String padName = safeInput("Remove:");
            PadManager.getInstance().removePad(padName);
            tabOfPads.removePad(new Pad(padName));
        });
        removeNote.addActionListener(ea -> {
            String noteName = safeInput("Remove");
            PadManager.getInstance().removeNote(noteName);
            tabOfPads.removeNote(new Note(noteName));
        });
        remove.add(removePad);
        remove.add(removeNote);
        this.add(remove);
    }

    private void addChangeItems() {
        JMenuItem changePad = new JMenuItem("Change Pad name");
        JMenuItem changeNote = new JMenuItem("Change Note name");
        JMenu change = new JMenu("Change...");
        changePad.addActionListener(ea -> {
            String newPadName = safeInput("New name");
            String oldName = null;
            if (PadManager.getInstance().getSelectedPad() != null) {
                oldName = PadManager.getInstance().getSelectedPad().getPadTitle();
            }
            PadManager.getInstance().changePadTitle(newPadName);
            tabOfPads.changePad(newPadName, oldName);
        });
        changeNote.addActionListener(ea -> {
            String newNoteName = safeInput("New name");
            String oldName = null;
            if (PadManager.getInstance().getSelectedPad() != null
                    && PadManager.getInstance().getSelectedPad().getSelectedNote() != null) {
                oldName = PadManager.getInstance().getSelectedPad().getSelectedNote().getNoteTitle();
            }
            PadManager.getInstance().changeNoteTitle(newNoteName);
            tabOfPads.changeNote(newNoteName, oldName);
        });
        change.add(changePad);
        change.add(changeNote);
        this.add(change);
    }

    private String safeInput(String str) {
        try {
            return input(str);
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        return "";
    }

    private String input(String str) {
        String input = JOptionPane.showInputDialog(mainWindow, str);
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    public static void debugPM() {
        System.out.println(PadManager.getInstance().getListOfPad().size());
        if (PadManager.getInstance().getSelectedPad() != null) {
            System.out.println(PadManager.getInstance().getSelectedPad().getPadTitle());
            System.out.println(PadManager.getInstance().getSelectedPad().getListOfNotes().size());
        }
    }

}
