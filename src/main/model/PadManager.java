package model;

import exception.DoesNotExistException;
import exception.ExistingTitleException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// Class responsible for combining pads and notes (uses singleton pattern)
public class PadManager {

    private static PadManager instance;
    private Pad selectedPad;
    private Set<Pad> listOfPad;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String jsonStore = "";

    private PadManager() {
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        this.listOfPad = new HashSet<>();
        this.selectedPad = null;
    }

    public static PadManager getInstance() {
        if (instance == null) {
            instance = new PadManager();
        }
        return instance;
    }

    public Pad getSelectedPad() {
        return this.selectedPad;
    }

    public Set<Pad> getListOfPad() {
        return this.listOfPad;
    }

    // Important: exception thrown if pad already exists
    public void newPad(String input) {
        Pad p = new Pad(input);
        if (getListOfPad().contains(p)) {
            throw new ExistingTitleException("This Pad already exists");
        }
        getListOfPad().add(p);
    }

    public void newNote(String input) {
        checkSelectedPad();
        getSelectedPad().addNote(new Note(input));
    }

    // Important: if cannot find pad then throw an exception
    public void removePad(String input) {
        Pad p = new Pad(input);
        if (!getListOfPad().contains(p)) {
            throw new DoesNotExistException("This Pad does not exist");
        }
        getListOfPad().remove(p);
        if (getSelectedPad().equals(p)) {
            this.selectedPad = null;
        }
    }

    public void removeNote(String input) {
        checkSelectedPad();
        getSelectedPad().removeNote(new Note(input));
    }

    
    public void changeNoteText(String input) {
        checkSelectedPad();
        if (getSelectedPad().getSelectedNote() == null) {
            throw new DoesNotExistException("Must select a note");
        }
        getSelectedPad().getSelectedNote().changeNoteText(input);
    }

    public void changePadTitle(String input) {
        checkSelectedPad();
        if (this.listOfPad.contains(new Pad(input))) {
            throw new ExistingTitleException("Pad already exists");
        }
        getSelectedPad().changePadTitle(input);
    }

    public void changeNoteTitle(String input) {
        checkSelectedPad();
        getSelectedPad().changeSelectedNoteTitle(input);
    }

    public void selectPad(String input) {
        Pad p = new Pad(input);
        if (!getListOfPad().contains(p)) {
            throw new DoesNotExistException("Pad does not exist");
        }
        selectPad(p);
    }

    private void selectPad(Pad pad) {
        for (Pad p : getListOfPad()) {
            if (p.equals(pad)) {
                this.selectedPad = p;
            }
        }
    }

    public void selectNote(String input) {
        checkSelectedPad();
        getSelectedPad().selectNote(new Note(input));
    }

    public void deselectPad() {
        this.selectedPad = null;
    }

    public void deselectNote() {
        checkSelectedPad();
        getSelectedPad().selectNote(null);
    }

    // EFFECTS: saves the selected pad to file
    public void saveSelectedPad() throws FileNotFoundException {
        checkSelectedPad();
        this.jsonStore = "./data/" + this.selectedPad.getPadTitle() + ".json";
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        jsonWriter.open();
        jsonWriter.write(this.selectedPad);
        jsonWriter.close();
    }

    // EFFECTS: loads a pad from file and sets it as selected pad
    public void loadPad(String input) throws IOException, NullPointerException {
        this.jsonStore = "./data/" + input + ".json";
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
        this.listOfPad.add(jsonReader.read());
    }



    private void checkSelectedPad() {
        if (getSelectedPad() == null) {
            throw new DoesNotExistException("Must make/select pad first");
        }
    }
}