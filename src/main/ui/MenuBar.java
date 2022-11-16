package ui;

import model.Note;
import model.Pad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
public class MenuBar extends JMenuBar {

    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem newPad;
    private JMenuItem newNote;
    private PadRow padRow;
    private CLI vm;


    public MenuBar() {
        super();
        file = new JMenu("File");
        open = new JMenuItem("Open...");
        save = new JMenuItem("Save Pad");
        newPad = new JMenuItem("New Pad");
        newNote = new JMenuItem("Add New Note");
        addFunctionalityToNewPad();
        addFunctionalityToNewNote();
        file.add(open);
        file.add(save);
        file.add(newPad);
        file.add(newNote);
        this.add(file);
    }

    public void setPadRow(PadRow pd) {
        this.padRow = pd;
    }

    public void setVimPad(CLI vm) {
        this.vm = vm;
    }

    public void addFunctionalityToOpen() {
    }

    public void addFunctionalityToSave() {
    }

    public void addFunctionalityToNewPad() {
        newPad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String padName = JOptionPane.showInputDialog("Enter title");
                Pad pad = new Pad(padName);
                vm.getListOfPad().add(pad);
                CenterNoteAndText centerNoteAndText = new CenterNoteAndText();
                padRow.addTab(pad.getPadTitle(), centerNoteAndText);
            }
        });
    }

    public void addFunctionalityToNewNote() {
        newNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteName = JOptionPane.showInputDialog("Enter title");
                Note note = new Note(noteName);
                CenterNoteAndText centerNoteAndText = (CenterNoteAndText) padRow.getSelectedComponent();
                centerNoteAndText.addNoteButton(note);
                vm.getSelectedPad().addNote(note);
                padRow.setSelectedComponent(centerNoteAndText);
            }
        });
    }
}

 */
