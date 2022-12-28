package ui;

import javax.swing.JTabbedPane;

import model.Pad;
import model.PadManager;

public class TabOfPads extends JTabbedPane {

    public TabOfPads() {
        super();
    }

    public void addPadToTab(Pad pad) {
        this.addTab(pad.getPadTitle(), new NotesAndText(pad));
    }
}
