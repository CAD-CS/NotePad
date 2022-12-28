package ui;

import javax.swing.JTabbedPane;

import model.Pad;
import model.PadManager;

public class TabOfPads extends JTabbedPane {

    public TabOfPads() {
        super();
    }

    public void addPadToTab(Pad pad) {
        NotesAndText nat = new NotesAndText();
        this.addTab(pad.getPadTitle(),nat);
    }
}
