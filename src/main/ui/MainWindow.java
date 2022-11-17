package ui;

import model.Note;
import model.Pad;
import model.VimPad;

import javax.swing.*;
import java.awt.*;

// Responsible for creating the main window for the graphical user interface
//

public class MainWindow extends JFrame {

    private VimPad vm;
    private MenuBar menuBar;
    private PadRow padRow;

    public MainWindow() {
        super("VimPad");
        runMainWindow();
    }

    //  MODIFIES: this
    //  EFFECTS: initializes and runs main window, and also deals with the exceptions
    public void runMainWindow() {
        vm = new VimPad();
        initialize();
    }


    // MODIFIES: this
    // EFFECTS: initializes the main window, menu bar, and padRow
    private void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        initializeMenuBar();
    }

    //  EFFECTS: initializes the menu bar
    private void initializeMenuBar()  {
        menuBar = new MenuBar(vm, this);
        this.setJMenuBar(menuBar);
        padRow = new PadRow(this, vm);
        menuBar.setPadRow(padRow);
        this.add(padRow, BorderLayout.NORTH);
    }

    public void addPadToTab(Pad pad) {
        CenterNoteAndText centerNoteAndText = new CenterNoteAndText(vm);
        for (Note note : pad.getListOfNotes()) {
            centerNoteAndText.addNoteButton(note);
        }
        padRow.addTab(pad.getPadTitle(),centerNoteAndText);
    }

    // EFFECTS:  deals with the exceptions
    public void dealWithException(Exception e) {
        if (e.getMessage().isBlank() || e.getMessage() == null || e.getMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Invalid");
        } else {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }




}
