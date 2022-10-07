package model;

//Represents a note containing a title and text
public class Note {
    private String noteTitle;
    private String text;

    // REQUIRES: String not be empty
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

    // REQUIRES: String not be empty
    // MODIFIES: this
    // EFFECTS: Changes the title of the note
    public void changeNoteTitle(String newTitle) {
        //stub
    }

    // REQUIRES: String not be empty
    // MODIFIES: this
    // EFFECTS: Modifies text
    public void modifyText(String newText) {
        //stub
    }
}
