package ui;

import model.Note;
import model.Pad;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// Class responsible for running and displaying in console
// ATTRIBUTION: Roughly modeled off of TellerApp class
//              in given instructions for Phase 1 with changed implementation and names for readability
public class VimPad {

    // Scanner
    Scanner userInput = new Scanner(System.in);

    // Fields
    private Note selectedNote;
    private Pad selectedPad;
    private String input;
    private boolean run;
    private ArrayList<Pad> listOfPad;

    // Runs VimPad application
    public VimPad() {
        runVimPad();
    }

    // EFFECTS: Displays the notes/pads and allows for modifying the fields
    private void runVimPad() {
        System.out.println("###Start Program###");
        System.out.println("\n__________________________________________________________________\n");
        introArt();
        System.out.println("\n__________________________________________________________________\n");
        initialize();
        displayCommands();
        while (run) {
            this.input = userInput.nextLine();
            System.out.println("************* \n");
            processMain(input);
            displayInventory();
            System.out.println("************* \n");
        }
        System.out.println("###End Program###");
    }

    // Getters
    public Note getSelectedNote() {
        return this.selectedNote;
    }

    public Pad getSelectedPad() {
        return this.selectedPad;
    }

    public String getInput() {
        return this.input;
    }

    public boolean getRun() {
        return this.run;
    }

    // Methods
    private void displayCommands() {
        System.out.println("Legend:");
        System.out.println("\t Quit = q");
        System.out.println("\t Commands = d");
        System.out.println("\t New note = n");
        System.out.println("\t New pad = p");
        System.out.println("\t Remove pad = rp");
        System.out.println("\t Remove note = rn");
        System.out.println("\t Select pad = sp");
        System.out.println("\t Select note = sn");
        System.out.println("\t Modify note = m");
        System.out.println("\t Add note to pad = a");
        System.out.println("\t Display note text = t");
    }

    @SuppressWarnings("methodlength")
    // REQUIRES: A valid input from the list [n,p,rp,rn,sn,sp,q,d,m,a]
    // Modifies: this
    // EFFECTS: processes command based on input and calls more specific method to deal with subclass of input
    private void processMain(String cmd) {
        if (cmd.equals("n") || cmd.equals("p")) {
            processNew(cmd);
        } else if (cmd.equals("rn") || cmd.equals("rp")) {
            processRemove(cmd);
        } else if (cmd.equals("sn") || cmd.equals("sp")) {
            processSelect(cmd);
        } else {
            switch (cmd) {
                case "q":
                    this.run = false;
                    break;
                case "d":
                    displayCommands();
                    break;
                case "a":
                    processAdd();
                    break;
                case "m":
                    processModify();
                    break;
                case "t":
                    System.out.println(this.selectedNote.getText() + "\n");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    // REQUIRES: Cmd be either n or p and the name of note or pad not be pre-existing
    // MODIFIES: this
    // EFFECTS: either creates a new Note and sets it as the selected note or creates a new pad, sets it as the
    //          selected pad and adds it to the list of pads
    private void processNew(String cmd) {
        if (cmd.equals("n")) {
            this.selectedNote = new Note(processOutAndInput("Title:"));
        } else {
            this.selectedPad = new Pad(processOutAndInput("Title:"));
            this.listOfPad.add(this.selectedPad);
        }
    }

    // REQUIRES: Cmd be either rn or rp
    // MODIFIES: this
    // EFFECTS: either removes a note from the list of notes of the selected pad or removes the selected pad
    //          from the list of pads
    private void processRemove(String cmd) {
        if (cmd.equals("rn")) {
            this.selectedPad.removeNote(this.selectedNote);
        } else {
            removePad(this.selectedPad);
        }
    }

    // REQUIRES: Cmd be either sn or sp
    // MODIFIES: this
    // EFFECTS: either selects a note from the list of notes from the selected pad or selects a pad
    //          from the list of pads
    private void processSelect(String cmd) {
        if (cmd.equals("sn")) {
            selectNote(processOutAndInput("Select:"));
        } else {
            selectPad(processOutAndInput("Select:"));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds selected note to the list of notes of the selected pad
    private void processAdd() {
        this.selectedPad.addNote(this.selectedNote);
    }

    // MODIFIES: this
    // EFFECTS: outputs selected note's text, asks user to input new text and stores the text as the note's
    //          new text
    private void processModify() {
        System.out.println("Old Text: ");
        System.out.println(this.selectedNote.getText());
        System.out.println("New Text: \n");
        this.input = userInput.nextLine();
        this.selectedNote.changeNoteText(this.input);
    }

    // EFFECTS: ask user for their input given "instruction text" and stores their answer in input
    private String processOutAndInput(String x) {
        System.out.println("\n " + x);
        this.input = userInput.nextLine();
        return this.input;
    }

    // MODIFIES: this
    // EFFECTS: uses input to find selected pad from the list of pads
    private void selectPad(String select) {
        for (Pad p : this.listOfPad) {
            if (Objects.equals(p.getPadTitle(), select)) {
                this.selectedPad = p;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: uses input to find selected note from the list of notes of the selected pad
    private void selectNote(String select) {
        for (Note n : this.selectedPad.getListOfNotes()) {
            if (Objects.equals(n.getNoteTitle(), select)) {
                this.selectedNote = n;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes selected pad from list of pads
    private void removePad(Pad padToRemove) {
        this.listOfPad.removeIf(p -> padToRemove == p);
    }

    // MODIFIES: this
    // EFFECTS: initializes the program with blank note, pad, input, and list of pad
    private void initialize() {
        this.selectedNote = new Note("");
        this.selectedPad = new Pad("");
        this.input = "";
        this.run = true;
        this.listOfPad = new ArrayList<>();
    }

    // EFFECTS: displays current selected items, and list of pad
    public void displayInventory() {
        System.out.println("\n--Selected Pad-- " + this.selectedPad.getPadTitle());
        System.out.println("--Selected Note-- " + this.selectedNote.getNoteTitle());
        System.out.println("--List of pads-- ");
        for (Pad p : this.listOfPad) {
            System.out.println("\t" + p.getPadTitle());
        }
        System.out.println("List of Notes in Pad: ");
        for (Note n : this.selectedPad.getListOfNotes()) {
            System.out.println("\t" + n.getNoteTitle());
        }
    }


    // Intro and outro methods

    // EFFECTS: displays the intro logo
    public void introArt() {
        System.out.println("__  __     _____     ___  ___      _______      ____       ___");
        System.out.println("| | | |     | |      |  | |  |     |  __  |    | __ |     |  _ \\");
        System.out.println("| | | |     | |      |  | |  |     | |__| |    ||__||     | | \\  |");
        System.out.println("| | | |     | |      |  \\_/  |     |    __|    |    |     | |  | |");
        System.out.println("| | | |     | |      |  | |  |     |   |       |  _ |     | |_/ |");
        System.out.println("\\ \\/  |     | |      |  | |  |     |   |       | || |     |    /");
        System.out.println(" \\__/      _|_|_     |__| |__|     |___|       |_||_|     |___/" + "\n");
    }
}
