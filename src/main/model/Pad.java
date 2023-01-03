package model;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.DoesNotExistException;
import exception.ExistingTitleException;
import exception.NotePadException;
import persistence.Writable;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

// Represents a Pad with a title, list of notes and a selected note 
public class Pad implements Writable {

    private String padTitle;
    private Set<Note> listOfNotes;
    private Note selectedNote;

    public Pad(String title) {
        if (title.isBlank()) {
            throw new NotePadException("Title cannot be empty");
        }
        this.padTitle = title;
        this.listOfNotes = new HashSet<>();
        this.selectedNote = null;
    }

    public String getPadTitle() {
        return this.padTitle;
    }

    public Set<Note> getListOfNotes() {
        return this.listOfNotes;
    }

    public Note getSelectedNote() {
        return this.selectedNote;
    }

    public void changePadTitle(String newTitle) {
        if (newTitle.isBlank()) {
            throw new NotePadException("Title cannot be empty");
        }
        this.padTitle = newTitle;
    }

    public void changeSelectedNoteTitle(String newTitle) {
        checkNote();
        if (this.listOfNotes.contains(new Note(newTitle))) {
            throw new ExistingTitleException("This note already exists");
        }
        getSelectedNote().changeNoteTitle(newTitle);
    }

    public void changeSelectedNoteText(String newText) {
        checkNote();
        getSelectedNote().changeNoteText(newText);
    }

    // Important: Cannot have the same title as any preexisting notes else throws a
    // runtime exception
    public void addNote(Note theNote) {
        if (getListOfNotes().contains(theNote)) {
            throw new ExistingTitleException("This Note already exists in this Pad");
        }
        this.listOfNotes.add(theNote);
    }

    // Important: Removes a note from the list of notes, if not found then throws a
    // runtime exception
    public void removeNote(Note theNote) {
        if (!isInList(theNote)) {
            throw new DoesNotExistException("This Note does not exist in this Pad");
        }
        this.listOfNotes.remove(theNote);
    }

    private boolean isInList(Note note) {
        for (Note n : getListOfNotes()) {
            if (n.equals(note)) {
                return true;
            }
        }
        return false;
    }

    // Important: Selects given note from list of notes else throws new runtime
    // exception
    public void selectNote(Note note) {
        if (!getListOfNotes().contains(note) && note != null) {
            throw new DoesNotExistException("This Note does not exist in this Pad");
        }
        for (Note n : getListOfNotes()) {
            if (n.equals(note)) {
                this.selectedNote = n;
                return;
            }
        }
        this.selectedNote = null;
    }

    private void checkNote() {
        if (this.selectedNote == null) {
            throw new DoesNotExistException("Must select note");
        }

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("padTitle", getPadTitle());
        json.put("listOfNotes", notesToJson());
        json.put("selectedNote", getSelectedNote().toJson());
        return json;
    }

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
        if (!(o instanceof Pad)) return false;
        Pad pad = (Pad) o;
        return Objects.equals(getPadTitle(), pad.getPadTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPadTitle());
    }
}