package ui;

import model.Event;
import model.EventLog;
import model.Note;
import model.Pad;
import model.VimPad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

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
        initializeWindowLog();
    }

    private void initializeWindowLog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                iterateOut();
                super.windowClosing(e);
            }
        });
    }



    // EFFECTS: Uses iterator to iterate and print EventLog
    private void iterateOut() {
        for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
            Event event = it.next();
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the main window, menu bar, and padRow
    private void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        this.getContentPane().setBackground(Color.BLACK);
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
        padRow.setSelectedIndex(padRow.findTabWithName(pad.getPadTitle()));
        try {
            vm.selectPad(padRow.getTitleAt(padRow.getSelectedIndex()));
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    // EFFECTS:  deals with the exceptions
    public void dealWithException(Exception e) {
        throw new RuntimeException(e);
    }






}