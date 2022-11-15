package model;

import exception.ExistingTitleException;
import exception.NotFoundException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

// Class responsible for combining pads and notes
public class VimPad {

    private Note selectedNote;
    private Pad selectedPad;
    private ArrayList<Pad> listOfPad;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String jsonStore = "./data/pad.json";


    public VimPad() {
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        this.listOfPad = new ArrayList<>();
    }

    // Getters & setters
    public Note getSelectedNote() {
        return this.selectedNote;
    }

    public void setSelectedNote(Note n) {
        this.selectedNote = n;
    }

    public Pad getSelectedPad() {
        return this.selectedPad;
    }

    public void setSelectedPad(Pad p) {
        this.selectedPad = p;
    }

    public ArrayList<Pad> getListOfPad() {
        return this.listOfPad;
    }

    public void setListOfPad(ArrayList<Pad> lop) {
        this.listOfPad = lop;
    }

    // Modifies: this
    // EFFECTS: processes command based on input and calls more specific method to deal with subclass of input
    public void processMain(String cmd, String input) throws IllegalArgumentException, ExistingTitleException,
            IOException, NullPointerException, NotFoundException {
        if (cmd.equals("n") || cmd.equals("p") || cmd.equals("rn") || cmd.equals("rp")) {
            processNewAndRemove(cmd, input);
        } else if (cmd.equals("sn") || cmd.equals("sp")) {
            processSelect(cmd, input);
        } else if (cmd.equals("m")) {
            processModify(input);
        } else if (cmd.equals("cn") || cmd.equals("cp")) {
            processChangeTitle(cmd, input);
        } else if (cmd.equals("s") || cmd.equals("l")) {
            processJson(cmd, input);
        } else {
            throw new IllegalArgumentException("Invalid Argument");
        }
    }


    // EFFECTS: this
    // MODIFIES: calls processNew if n or p, or processRemove if rp or rn
    private void processNewAndRemove(String cmd, String input) throws  ExistingTitleException {
        if (cmd.equals("n") || cmd.equals("p")) {
            processNew(cmd, input);
        } else {
            processRemove(cmd);
        }
    }

    // MODIFIES: this
    // EFFECTS: either creates a new Note and adds it to the current pad, or creates a new pad and add it to
    //          the list of pads. If a new note is created without an existing pad then a NullPointerException will be
    //          thrown, will also be thrown if no pad is selected. ExistingTitleException Thrown if lop already contain
    //          name or pad contains name of note
    private void processNew(String cmd, String input) throws NullPointerException, ExistingTitleException {
        if (cmd.equals("n")) {
            if (getSelectedPad() == null) {
                throw new NullPointerException();
            } else if (isAlreadyInListOfNotes(input)) {
                throw new ExistingTitleException();
            }
            getSelectedPad().addNote(new Note(input));
        } else if (isAlreadyInListOfPads(input)) {
            throw new ExistingTitleException();
        } else {
            getListOfPad().add(new Pad(input));
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
    // EFFECTS: Removes selected pad from list of pads
    private void removePad(Pad padToRemove) {
        this.listOfPad.removeIf(p -> padToRemove == p);
    }


    // MODIFIES: this
    // EFFECTS: outputs selected note's text, asks user to input new text and stores the text as the note's
    //          new text
    private void processModify(String input) {
        this.selectedNote.changeNoteText(input);
    }


    // MODIFIES: this
    // EFFECTS: either selects a note from the list of notes from the selected pad or selects a pad
    //          from the list of pads
    private void processSelect(String cmd, String input) throws NotFoundException {
        if (cmd.equals("sn")) {
            selectNote(input);
        } else {
            selectPad(input);
        }
    }


    // MODIFIES: this
    // EFFECTS: uses input to find selected pad from the list of pads
    private void selectPad(String select) throws NotFoundException {
        boolean found = false;
        for (Pad p : this.listOfPad) {
            if (Objects.equals(p.getPadTitle(), select)) {
                this.selectedPad = p;
                found = true;
            }
        }
        if (!found) {
            throw new NotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: uses input to find selected note from the list of notes of the selected pad
    private void selectNote(String select) throws NotFoundException {
        boolean found = false;
        for (Note n : this.selectedPad.getListOfNotes()) {
            if (Objects.equals(n.getNoteTitle(), select)) {
                this.selectedNote = n;
                found = true;
            }
        }
        if (!found) {
            throw new NotFoundException();
        }
    }


    // Modifies: this
    // Effects: Saves or loads pad to or from file
    private void processJson(String cmd, String input) throws FileNotFoundException,
            NullPointerException, IOException {
        if (cmd.equals("s")) {
            saveSelectedPad();
        } else {
            loadPad(input);
        }
    }

    // EFFECTS: saves the selected pad to file
    private void saveSelectedPad() throws FileNotFoundException {
        this.jsonStore = "./data/" + this.selectedPad.getPadTitle() + ".json";
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        jsonWriter.open();
        jsonWriter.write(this.selectedPad);
        jsonWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: loads a pad from file and sets it as selected pad
    private void loadPad(String input) throws IOException, NullPointerException {
        this.jsonStore = "./data/" + input + ".json";
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        this.selectedPad = jsonReader.read();
    }


    // MODIFIES: this
    // EFFECTS: changes name of selected note or pad, throw IAE if empty, throw ETE if there exists a note or pad
    //          with same name
    private void processChangeTitle(String cmd, String input) throws IllegalArgumentException, ExistingTitleException {
        if (input.equals("") || input.equals(null)) {
            throw new IllegalArgumentException();
        } else if (isAlreadyInListOfPads(input) || isAlreadyInListOfNotes(input)) {
            throw new ExistingTitleException();
        } else if (cmd.equals("cn")) {
            this.selectedNote.changeNoteTitle(input);
        } else {
            this.selectedPad.changePadTitle(input);
        }
    }

}
