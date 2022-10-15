package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    Note note;

    @BeforeEach
    public void setup() {
        note = new Note("Start");
    }

    @Test
    public void TestChangeNoteTitle() {
        note.changeNoteTitle("Test");

        assertEquals("Test" , note.getNoteTitle());
    }

    @Test
    public void TestChangeNoteText() {
        assertEquals(null , note.getText());
        note.changeNoteText("Test Text");
        assertEquals("Test Text" , note.getText());
    }
}
