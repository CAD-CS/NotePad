package model;


import java.util.ArrayDeque;
import java.util.ArrayList;

// Represents a pad containing a list of notes, and a title
public class Pad {

    private String padTitle;
    private ArrayList<Note> listOfNotes;

    // REQUIRES: a non-empty string
    // MODIFIES: this
    // EFFECTS: Creates a new pad with a title and an empty list of notes
    public Pad(String title) {
        this.padTitle = title;
        this.listOfNotes = new ArrayList<Note>();
    }

    public String getPadTitle() {
        return this.padTitle;
    }

    public ArrayList<Note> getListOfNotes() {
        return this.listOfNotes;
    }

    // REQUIRES: Cannot have the same title as any preexisting notes
    // MODIFIES: this
    // EFFECTS: Adds a note to the list of notes if not already there, if else not do anything
    public void addNote(Note theNote) {
        //stub
    }


    // REQUIRES: Cannot have the same title as any preexisting notes
    // MODIFIES: this
    // EFFECTS: removes a note to the list of notes if note already there, if else not do anything
    public void removeNote(Note theNote) {
        //stub
    }

    // REQUIRES: A preexisting note with the same name
    // MODIFIES: none
    // EFFECTS: finds a note with the given name and produces its contents
    public Note selectNote(Note theNote) {
        //stub
    }


    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns true if a note with the same name in the pad
    public boolean isInPad(Note theNote) {
        //stub
    }
}
