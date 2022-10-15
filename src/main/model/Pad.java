package model;


import java.util.ArrayList;
import java.util.Objects;

// Represents a pad containing a list of notes, and a title
public class Pad {

    private String padTitle;
    private ArrayList<Note> listOfNotes;

    // REQUIRES: Cannot create note with same names
    // MODIFIES: this
    // EFFECTS: Creates a new pad with a title and an empty list of notes
    public Pad(String title) {
        this.padTitle = title;
        this.listOfNotes = new ArrayList<>();
    }

    public String getPadTitle() {
        return this.padTitle;
    }

    public ArrayList<Note> getListOfNotes() {
        return this.listOfNotes;
    }


    // REQUIRES: Cannot be the name of a pre-existing title
    // MODIFIES: this
    // EFFECTS: changes the name of the pad title
    public void changePadTitle(String newTitle) {
        this.padTitle = newTitle;
    }

    // REQUIRES: Cannot have the same title as any preexisting notes
    // MODIFIES: this
    // EFFECTS: Adds a note to the list of notes if not already there, if else not do anything
    public void addNote(Note theNote) {

        if (!isInPad(theNote, this.listOfNotes)) {
            this.listOfNotes.add(theNote);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a note to the list of notes if note already there, if else not do anything
    public void removeNote(Note theNote) {
        if (isInPad(theNote, this.listOfNotes)) {
            this.listOfNotes.remove(theNote);
        }
    }

    // EFFECTS: returns true if a note with the same name in the pad
    private boolean isInPad(Note theNote, ArrayList<Note> lon) {
        String nameOfNote = theNote.getNoteTitle();
        for (Note n : lon) {
            if (Objects.equals(nameOfNote, n.getNoteTitle())) {
                return true;
            }
        }
        return false;
    }
}
