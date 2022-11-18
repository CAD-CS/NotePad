package ui;

import model.VimPad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {

    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem newPad;
    private JMenuItem newNote;
    private JMenuItem removePad;
    private JMenuItem removeNote;
    private PadRow padRow;
    private VimPad vm;
    private MainWindow window;


    public MenuBar(VimPad vm, MainWindow window) {
        super();
        this.vm = vm;
        this.window = window;
        addFunctionality();
        file.add(open);
        file.add(save);
        file.add(newPad);
        file.add(newNote);
        file.add(removePad);
        file.add(removeNote);
        this.add(file);
    }

    public void addFunctionality() {
        file = new JMenu("File");
        open = new JMenuItem("Open...");
        save = new JMenuItem("Save Pad");
        newPad = new JMenuItem("New Pad");
        newNote = new JMenuItem("Add New Note");
        removePad = new JMenuItem("Close Pad");
        removeNote = new JMenuItem("Remove Note From Pad");
        addFunctionalityToNewPad();
        addFunctionalityToNewNote();
        addFunctionalityToOpen();
        addFunctionalityToSave();
        addFunctionalityToRemovePad();
        addFunctionalityToRemoveNote();
    }

    public void addFunctionalityToOpen() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter File name");
                try {
                    vm.processMain("l", fileName);
                    vm.selectPad(fileName);
                    window.addPadToTab(vm.getSelectedPad());
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void addFunctionalityToSave() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(window,"Saved: " + padRow.getTitleAt(padRow.getSelectedIndex()));
                try {
                    vm.processMain("s", "");
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void addFunctionalityToNewPad() {
        newPad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String padName = JOptionPane.showInputDialog("Enter title");
                try {
                    vm.processMain("p", padName);
                    CenterNoteAndText centerNoteAndText = new CenterNoteAndText(vm);
                    padRow.addTab(padName, centerNoteAndText);
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void addFunctionalityToNewNote() {
        newNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteName = JOptionPane.showInputDialog("Enter title");
                try {
                    vm.processMain("sp", padRow.getTitleAt(padRow.getSelectedIndex()));
                    vm.processMain("n", noteName);
                    vm.selectNote(noteName);
                    CenterNoteAndText centerNoteAndText = (CenterNoteAndText) padRow.getSelectedComponent();
                    centerNoteAndText.addNoteButton(vm.getSelectedNote());
                    vm.getSelectedPad().addNote(vm.getSelectedNote());
                    vm.processMain("dsn", "");
                    padRow.setSelectedComponent(centerNoteAndText);
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void addFunctionalityToRemovePad() {
        removePad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String padName = JOptionPane.showInputDialog("Close");
                try {
                    padRow.removeTabAt(padRow.findTabWithName(padName));
                    vm.processMain("rp", padName);
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void addFunctionalityToRemoveNote() {
        removeNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteName = JOptionPane.showInputDialog("Remove");
                try {
                    vm.processMain("rn", noteName);
                    CenterNoteAndText centerNoteAndText = (CenterNoteAndText) padRow.getSelectedComponent();
                    centerNoteAndText.removeNoteButton(noteName);
                    padRow.setSelectedComponent(centerNoteAndText);
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
    }

    public void setPadRow(PadRow pd) {
        this.padRow = pd;
    }
}
