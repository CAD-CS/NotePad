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

    // Fields
    private Note selectedNote;
    private Pad selectedPad;
    private String input;
    private boolean run;
    private ArrayList<Pad> listOfPad;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String jsonStore = "./data/pad.json";

    // Runs VimPad application
    public VimPad() throws FileNotFoundException {
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
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

    // EFFECTS: this
    // MODIFIES: calls processNew if n or p, or processRemove if rp or rn
    private void processNewAndRemove(String cmd) {
        if (cmd.equals("n") || cmd.equals("p")) {
            processNew(cmd);
        } else {
            processRemove(cmd);
        }
    }

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

    // EFFECTS: this
    // MODIFIES: calls processAdd if a, or processModify m
    private void processAddAndModify(String cmd) {
        if (cmd.equals("a")) {
            processAdd();
        } else {
            processModify();
        }
    }

    // MODIFIES: this
    // EFFECTS: either creates a new Note and sets it as the selected note or creates a new pad, sets it as the
    //          selected pad and adds it to the list of pads
    private void processNew(String cmd) {
        try {
            String newTitle = processOutAndInput("Title:");
            if (cmd.equals("n") && !isAlreadyInListOfNotes(newTitle)) {
                this.selectedNote = new Note(newTitle);
            } else if (cmd.equals("p") && !isAlreadyInListOfPads(newTitle)) {
                this.selectedPad = new Pad(newTitle);
                this.listOfPad.add(this.selectedPad);
            } else {
                System.out.println("*Preexisting Title*");
            }
        } catch (NullPointerException e) {
            System.out.println("*Title cannot be empty*");
        }
    }


    // EFFECTS: returns true if pad already exists
    private boolean isAlreadyInListOfPads(String title) {
        for (Pad p : this.listOfPad) {
            if (p.getPadTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if pad already exists
    private boolean isAlreadyInListOfNotes(String title) {
        for (Note n : this.selectedPad.getListOfNotes()) {
            if (n.getNoteTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

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

    // MODIFIES: this
    // EFFECTS: either selects a note from the list of notes from the selected pad or selects a pad
    //          from the list of pads
    private void processSelect(String cmd) {
        try {
            if (cmd.equals("sn")) {
                selectNote(processOutAndInput("Select:"));
            } else {
                selectPad(processOutAndInput("Select:"));
            }
        } catch (NullPointerException e) {
            System.out.println("*Please Enter a non null title*");
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
    private String processOutAndInput(String x) throws NullPointerException {
        System.out.println("\n " + x);
        String result = userInput.nextLine();
        if (result == null || result.equals("")) {
            throw new NullPointerException();
        }
        this.input = result;
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

    // EFFECTS: saves the selected pad to file
    private void saveSelectedPad() {
        try {
            this.jsonStore = "./data/" + this.selectedPad.getPadTitle() + ".json";
            jsonWriter = new JsonWriter(jsonStore);
            jsonReader = new JsonReader(jsonStore);
            jsonWriter.open();
            jsonWriter.write(this.selectedPad);
            jsonWriter.close();
            System.out.println("Saved " + this.selectedPad.getPadTitle() + " to " + jsonStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a pad from file and sets it as selected pad
    private void loadPad() {
        try {
            this.jsonStore = "./data/" + processOutAndInput("Enter name of pad: ") + ".json";
            jsonWriter = new JsonWriter(jsonStore);
            jsonReader = new JsonReader(jsonStore);
            this.selectedPad = jsonReader.read();
            System.out.println("Loaded " + this.selectedPad + " from " + jsonStore);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
        } catch (NullPointerException e) {
            System.out.println("*Please enter non null title*");
        }
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


}
