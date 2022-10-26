package ui;

import model.Note;
import model.Pad;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// Class responsible for running and displaying in console
// ATTRIBUTION[1]:  VimPad was Roughly modeled off of TellerApp class
//              in given instructions for Phase 1 with changed implementation and names for readability
// Attribution[2]: processJson, saveSelectedPad, loadPad were modelled with respect to the methods in
//                 "JsonSerializationDemo"
public class VimPad {

    // Scanner
    Scanner userInput = new Scanner(System.in);

    // Constants
    private static final String JSON_STORE = "./data/pad.json";

    // Fields
    private Note selectedNote;
    private Pad selectedPad;
    private String input;
    private boolean run;
    private ArrayList<Pad> listOfPad;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Runs VimPad application
    public VimPad() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
            System.out.println("\nEnter: \n");
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
        System.out.println("\t Save selected pad = s");
        System.out.println("\t Load pad = l");
    }

    // REQUIRES: A valid input from the list [n,p,rp,rn,sn,sp,q,d,m,a]
    // Modifies: this
    // EFFECTS: processes command based on input and calls more specific method to deal with subclass of input
    private void processMain(String cmd) {
        if (cmd.equals("n") || cmd.equals("p") || cmd.equals("rn") || cmd.equals("rp")) {
            processNewAndRemove(cmd);
        } else if (cmd.equals("sn") || cmd.equals("sp")) {
            processSelect(cmd);
        } else if (cmd.equals("a") || cmd.equals("m")) {
            processAddAndModify(cmd);
        } else if (cmd.equals("s") || cmd.equals("l")) {
            processJson(cmd);
        } else {
            processMisc(cmd);
        }
    }

    // REQUIRES: cmd be one of p,n,rp,rn
    // EFFECTS: this
    // MODIFIES: calls processNew if n or p, or processRemove if rp or rn
    private void processNewAndRemove(String cmd) {
        if (cmd.equals("n") || cmd.equals("p")) {
            processNew(cmd);
        } else {
            processRemove(cmd);
        }
    }

    // REQUIRES: cmd be one of q, d,t
    // EFFECTS: this
    // MODIFIES: calls processAdd if a, or processModify m
    private void processMisc(String cmd) {
        if (cmd.equals("q")) {
            this.run = false;
        } else if (cmd.equals("d")) {
            displayCommands();
        } else if (cmd.equals("t")) {
            System.out.println(this.selectedNote.getText() + "\n");
        } else {
            System.out.println("Invalid input");
        }
    }

    // REQUIRES: cmd be one of a or m
    // EFFECTS: this
    // MODIFIES: calls processAdd if a, or processModify m
    private void processAddAndModify(String cmd) {
        if (cmd.equals("a")) {
            processAdd();
        } else {
            processModify();
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

    // Requires: cmd be either s or l
    // Modifies: this
    // Effects: Saves or loads pad to or from file
    private void processJson(String cmd) {
        if (cmd.equals("s")) {
            saveSelectedPad();
        } else {
            loadPad();
        }
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
    private void displayInventory() {
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

    // EFFECTS: saves the selected pad to file
    private void saveSelectedPad() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.selectedPad);
            jsonWriter.close();
            System.out.println("Saved " + this.selectedPad.getPadTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a pad from file and sets it as selected pad
    private void loadPad() {
        try {
            this.selectedPad = jsonReader.read();
            System.out.println("Loaded " + this.selectedPad + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
