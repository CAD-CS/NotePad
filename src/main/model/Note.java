package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;


// Attribution[1]: toJson was created with respect to "JsonSerializationDemo"
// Represents a note containing a title and text
public class Note implements Writable {
    private String noteTitle;
    private String text;

    // MODIFIES: this
    // EFFECTS: creates a note with a title but with zero text (blank canvas)
    public Note(String title) {
        this.noteTitle = title;
        this.text = "";
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public String getText() {
        return text;
    }

    // MODIFIES: this
    // EFFECTS: Changes the title of the note
    public void changeNoteTitle(String newTitle) {
        this.noteTitle = newTitle;

    }

    // MODIFIES: this
    // EFFECTS: Changes the text of the note to the inputted text
    public void changeNoteText(String newText) {
        this.text = newText;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("noteTitle", this.noteTitle);
        json.put("text", this.text);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        Note note = (Note) o;
        return Objects.equals(getNoteTitle(), note.getNoteTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteTitle());
    }
}