package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Attribution[1]: toJson, notesToJson was modelled with respect to "JsonSerializationDemo"
// Represents a pad containing a list of notes, and a title
public class Pad implements Writable {

    private String padTitle;
    private ArrayList<Note> listOfNotes;

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
        for (Note n : lon) {
            if (n.equals(theNote)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("padTitle", this.padTitle);
        json.put("listOfNotes", notesToJson());
        return json;
    }

    // EFFECTS: returns notes in this pad as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Note n : this.listOfNotes) {
            jsonArray.put(n.toJson());
        }
        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pad))  {
            return false;
        }
        Pad pad = (Pad) o;
        return Objects.equals(getPadTitle(), pad.getPadTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPadTitle());
    }

}
