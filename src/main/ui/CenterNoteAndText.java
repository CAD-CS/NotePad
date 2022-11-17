package ui;

import model.Note;
import model.VimPad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterNoteAndText extends JPanel {

    private JTextArea noteText;
    private JPanel notes;
    private VimPad vm;

    public CenterNoteAndText(VimPad vm) {
        super(new BorderLayout());
        this.vm = vm;
        noteText = new JTextArea(100, 100);
        notes = new JPanel();
        notes.setBackground(Color.PINK);
        this.add(notes, BorderLayout.WEST);
        this.add(noteText, BorderLayout.CENTER);
    }

    public void addNoteButton(Note n) {
        JButton button = new JButton(n.getNoteTitle());
        button.setName(n.getNoteTitle());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noteText.setText(n.getText());
                try {
                    vm.selectNote(n.getNoteTitle());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }

            }
        });
        notes.add(button);
    }

    public void removeNoteButton(String noteName) {
        for (Component component : notes.getComponents()) {
            if (component.getName().equals(noteName)) {
                notes.remove(component);
            }
        }
    }
}
