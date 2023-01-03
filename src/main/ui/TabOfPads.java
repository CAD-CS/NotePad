package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Note;
import model.Pad;
import model.PadManager;

import java.awt.*;

public class TabOfPads extends JTabbedPane {

    public TabOfPads() {
        super();
        this.addChangeListener(e -> {
            int index = getSelectedIndex();
            if (index != -1) {
                PadManager.getInstance().selectPad(getTitleAt(index));
            }
        });
    }

    public void addPad(Pad pad) {
        NotesAndText notesAndText = new NotesAndText();
        notesAndText.convertPad(pad);
        this.add(pad.getPadTitle(), notesAndText);
    }

    public void addNote(Note note) {
        NotesAndText notesAndText = (NotesAndText) getComponentAt(getSelectedIndex());
        notesAndText.addNote(note);
    }

    public void removePad(Pad pad) {
        removeTabAt(findPad(pad.getPadTitle()));
    }

    public void removeNote(Note note) {
        NotesAndText notesAndText = (NotesAndText) getComponentAt(getSelectedIndex());
        notesAndText.removeNote(note);
    }

    public void changePad(String newName, String oldName) {
        setTitleAt(findPad(oldName), newName);
    }

    public void changeNote(String newName, String oldName) {
        NotesAndText notesAndText = (NotesAndText) getComponentAt(getSelectedIndex());
        notesAndText.changeNote(newName, oldName);
    }

    private int findPad(String name) {
        int count = getTabCount();
        for (int i = 0; i <= count; i++) {
            if (getTitleAt(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
