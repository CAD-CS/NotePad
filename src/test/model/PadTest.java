package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PadTest {

    Pad pad;
    Note n1 = new Note("Start1");
    Note n2 = new Note("Start2");
    Note n3 = new Note("Start3");

    @BeforeEach
    public void setup() {
        pad = new Pad("Start");
    }

    @Test
    public void testChangPadTitle() {
        pad.changePadTitle("End");
        assertEquals("End", pad.getPadTitle());
    }
    @Test
    public void testAddNoteTrue() {
        pad.addNote(n1);
        assertEquals(1,pad.getListOfNotes().size());
        pad.addNote(n2);
        assertEquals(2, pad.getListOfNotes().size());
    }

    @Test
    public void testAddNoteFalse() {
        pad.addNote(n1);
        pad.addNote(n2);
        pad.addNote(n2);
        pad.addNote(n1);
        assertEquals(2 , pad.getListOfNotes().size());
    }

    @Test
    public void testRemoveNoteTrue() {
        pad.addNote(n1);
        pad.addNote(n2);
        pad.removeNote(n1);
        assertEquals(1 , pad.getListOfNotes().size());
        pad.removeNote(n2);
        assertEquals(0 , pad.getListOfNotes().size());
    }

    @Test
    public void testRemoveNoteFalse() {
        pad.removeNote(n1);
        assertEquals(0 , pad.getListOfNotes().size());

        pad.addNote(n1);
        pad.addNote(n2);
        pad.removeNote(n3);
        assertEquals(2 , pad.getListOfNotes().size());
    }

    @Test
    public void testEquals() {
        assertFalse(pad.equals(n1));
    }

    @Test
    public void testEquals2() {
        assertTrue(pad.equals(pad));
    }

    @Test
    public void testHashcode() {
        assertEquals(80204897,pad.hashCode());
    }
}
