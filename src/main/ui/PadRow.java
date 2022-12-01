package ui;

import model.VimPad;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PadRow extends JTabbedPane {

    private MainWindow window;
    private VimPad vm;

    public PadRow(MainWindow window, VimPad vm) {
        super();
        this.window = window;
        this.vm = vm;
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    if (getSelectedIndex() != -1) {
                        vm.processMain("sp",getTitleAt(getSelectedIndex()));
                    }
                } catch (Exception exceptionx) {
                    throw new RuntimeException(exceptionx);
                }
            }
        });
    }

    public int findTabWithName(String name) {
        int count = this.getTabCount();
        for (int i = 0;i < count;i++) {
            if (name.equals(getTitleAt(i))) {
                return i++;
            }
        }
        return -1;
    }
}