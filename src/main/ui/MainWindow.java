package ui;

import model.Note;
import model.Pad;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

// Responsible for creating the main window for the graphical user interface
//
public class MainWindow extends JFrame {

    private CLI vimPad;
    private MenuBar menuBar;
    private PadRow padRow;

    public MainWindow() throws FileNotFoundException {
        super("CLI");
        initialize();
        vimPad = new CLI();
        System.out.println("dfd");
    }

    // MODIFIES: this
    // EFFECTS: initializes the main window, menu bar, and padrow
    private void initialize() throws FileNotFoundException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Jframe
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        menuBar = new MenuBar();
        this.setJMenuBar(menuBar);
        padRow = new PadRow();
        menuBar.setPadRow(padRow);
        this.add(padRow, BorderLayout.NORTH);
        menuBar.setVimPad(vimPad);


        Note n1 = new Note("note 1");
        n1.changeNoteText("Testing once");
        Note n2 = new Note("note 2");
        n2.changeNoteText("Testing twice");
        Pad p = new Pad("Pad one");
        p.addNote(n1);
        p.addNote(n2);
        addPadToTab(p);

    }

    private void addPadToTab(Pad pad) {
        CenterNoteAndText centerNoteAndText = new CenterNoteAndText();
        for (Note note : pad.getListOfNotes()) {
            centerNoteAndText.addNoteButton(note);
        }
        padRow.addTab(pad.getPadTitle(),centerNoteAndText);
    }




}
