package ui;

import model.Note;
import model.Pad;
import model.PadManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NotesAndText extends JTabbedPane {

    public NotesAndText() {
        super(JTabbedPane.LEFT);
        initialize();
    }

    private void initialize() {
        this.addChangeListener(e -> {
            int index = getSelectedIndex();
            if (index != -1) {
                PadManager.getInstance().selectNote(getTitleAt(index));
            }
        });
    }

    public void convertPad(Pad pad) {
        for (Note note : pad.getListOfNotes()) {
            addNote(note);
        }
    }

    public void addNote(Note note) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setText(note.getText());
        JScrollPane scroll  = new JScrollPane(textArea);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                PadManager.getInstance().getSelectedPad().getSelectedNote().changeNoteText(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                PadManager.getInstance().getSelectedPad().getSelectedNote().changeNoteText(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Do nothing
            }
        });
        add(note.getNoteTitle(), scroll);
    }

    public void removeNote(Note note) {
        removeTabAt(findNote(note.getNoteTitle()));
    }

    public void changeNote(String newName, String oldName) {
        setTitleAt(findNote(oldName), newName);
    }

    private int findNote(String noteName) {
        int count = getTabCount();
        for (int i = 0; i <= count; i++) {
            if (getTitleAt(i).equals(noteName)) {
                return i;
            }
        }
        return -1;
    }
}
