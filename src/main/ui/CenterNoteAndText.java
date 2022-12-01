package ui;

import model.Note;
import model.VimPad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CenterNoteAndText extends JPanel {

    private JTextArea noteText;
    private JPanel notes;
    private VimPad vm;

    public CenterNoteAndText(VimPad vm) {
        super(new BorderLayout());
        this.vm = vm;
        noteText = new JTextArea(100, 100);
        noteText.setBackground(new Color(23, 23, 23));
        noteText.setForeground(new Color(204, 142, 0));
        noteText.setCaretColor(new Color(255, 229, 229));
        noteText.setFont(new Font("terminal", Font.BOLD, 13));
        notes = new JPanel();
        notes.setBackground(new Color(40, 203, 18));
        this.add(notes, BorderLayout.WEST);
        this.add(noteText, BorderLayout.CENTER);
        addFunctionalityToNoteText();
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
            String name = component.getName();
            if (name.equals(noteName)) {
                notes.remove(component);
            }
        }
    }

    public void addFunctionalityToNoteText() {
        noteText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                vm.getSelectedNote().changeNoteText(noteText.getText());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Saved: " + noteText.getText());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Saved: " + noteText.getText());
            }
        });
    }
}