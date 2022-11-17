package ui;

import model.VimPad;

import javax.swing.*;

public class PadRow extends JTabbedPane {

    private MainWindow window;
    private VimPad vm;

    public PadRow(MainWindow window, VimPad vm) {
        super();
        this.window = window;
        this.vm = vm;
    }

    public int findTabWithName(String name) {
        int count = this.getTabCount();
        for (int i = 0;i < count;i++) {
            if (name.equals(getTitleAt(i))) {
                return i;
            }
        }
        return -1;
    }

}
