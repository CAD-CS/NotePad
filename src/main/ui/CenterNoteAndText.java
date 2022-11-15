package ui;

import model.Note;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterNoteAndText extends JPanel {

    private JTextArea noteText;
    private JPanel notes;

    public CenterNoteAndText() {
        super(new BorderLayout());
        noteText = new JTextArea(100, 100);
        notes = new JPanel();
        notes.setBackground(Color.PINK);
        this.add(notes, BorderLayout.WEST);
        this.add(noteText, BorderLayout.CENTER);
    }

    public void addNoteButton(Note n) {
        JButton button = new JButton(n.getNoteTitle());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noteText.setText(n.getText());
            }
        });
        notes.add(button);
    }

   /* public void updateNoteText() {
        noteText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    } */
}
