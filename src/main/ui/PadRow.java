package ui;

import model.VimPad;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                    vm.selectPad(getTitleAt(getSelectedIndex()));
                } catch (Exception exception) {
                    window.dealWithException(exception);
                }
            }
        });
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
