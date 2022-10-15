package model;

//Represents a note containing a title and text
public class Note {
    private String noteTitle;
    private String text;

    // REQUIRES: No pre-existing note with same name
    // MODIFIES: this
    // EFFECTS: creates a note with a title but with zero text (blank canvas)
    public Note(String title) {
        this.noteTitle = title;
        this.text = null;
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public String getText() {
        return text;
    }

    // REQUIRES: title cannot be that of a pre-existing one
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
}
