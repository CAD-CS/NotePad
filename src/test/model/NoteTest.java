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
    public void testChangeNoteTitle() {
        note.changeNoteTitle("Test");

        assertEquals("Test" , note.getNoteTitle());
    }

    @Test
    public void testChangeNoteText() {
        assertEquals("" , note.getText());
        note.changeNoteText("Test Text");
        assertEquals("Test Text" , note.getText());
    }

    @Test
    public void testEquals() {
        assertFalse(note.equals(1));
    }

    @Test
    public void testHashCode() {
        assertEquals(80204897, note.hashCode());
    }
}
