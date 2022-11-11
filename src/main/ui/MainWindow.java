package ui;

import javax.swing.*;
import java.awt.*;

// Responsible for creating the graphical user interface
//
public class MainWindow {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JFrame frame;
    private JPanel topRow;
    private JPanel leftColumn;
    private JPanel centreText;
    private JPanel rightInstructions;

    public MainWindow() {
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: initializes the window
    private void initialize() {
        initFrame();
        initTopRow();
        initLeftColumn();
        initCentreText();
        initRightInstructions();
    }

    // MODIFIES: this
    // EFFECTS: initializes the main frame
    private void initFrame() {
        this.frame = new JFrame("VimPad");
        this.frame.setSize(WIDTH,HEIGHT);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.getContentPane().setBackground(Color.GRAY);
        this.frame.setLayout(new BorderLayout(1,1));
    }

    // MODIFIES: this
    // EFFECTS: initializes the top row which contains the buttons for the corresponding list of pads
    private void initTopRow() {
        this.topRow = new JPanel();
        this.topRow.setBackground(Color.cyan);
        this.frame.add(this.topRow, BorderLayout.NORTH);
    }


    // MODIFIES: this
    // EFFECTS: initializes the left column which contains the buttons for the corresponding list of notes
    private void initLeftColumn() {
        this.leftColumn = new JPanel();
        this.leftColumn.setBackground(Color.GREEN);
        this.frame.add(this.leftColumn, BorderLayout.WEST);
    }


    // MODIFIES: this
    // EFFECTS: initializes the centre column which contains the text for the corresponding note
    private void initCentreText() {
        this.centreText = new JPanel();
        this.centreText.setBackground(Color.MAGENTA);
        this.frame.add(this.centreText, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes the left column which contains the instructions for the commands
    private void initRightInstructions() {
        this.rightInstructions = new JPanel();
        this.rightInstructions.setBackground(Color.RED);
        this.frame.add(this.rightInstructions, BorderLayout.EAST);
    }

}
