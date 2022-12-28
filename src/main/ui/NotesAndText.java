package ui;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Note;

public class NotesAndText extends JPanel {

    private Set<Note> listOfNote;
    private JTextArea text;

    public NotesAndText() {
        super(new BorderLayout());
        this.listOfNote = new HashSet<>();
        this.text = new JTextArea();
        initialize();
    }

    private void initialize() {
        this.add(listOfNote, BorderLayout.WEST);
    }
}
