package model;

import org.json.JSONObject;

import exception.NotePadException;
import persistence.Writable;

import java.util.Objects;


// Represents a note containing a title and text
public class Note implements Writable {
    private String noteTitle;
    private String text;

    // EFFECTS: creates a note with a title but with zero text (blank canvas)
    public Note(String title) {
        if (title.isBlank()) {
            throw new NotePadException("Title cannot be empty");
        }
        this.noteTitle = title;
        this.text = "";
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public String getText() {
        return text;
    }

    public void changeNoteTitle(String newTitle) {
        this.noteTitle = newTitle;
    }

    public void changeNoteText(String newText) {
        this.text = newText;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("noteTitle", getNoteTitle());
        json.put("text", getText());
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