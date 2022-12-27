package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DoesNotExistException;
import exception.ExistingTitleException;

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
    public void constructorTest() {
        try {
            Pad p = new Pad("");
            fail();
        } catch (Exception e) {
            // Pass
        }
    }

    @Test
    public void changPadTitleTest() {
        try {
            Pad p = new Pad("");
            fail();
        } catch (Exception e) {
            // Pass
        }

        try {
            pad.changePadTitle("End");
            assertEquals("End", pad.getPadTitle());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void changeSelectedNoteTitleTest() {
        pad.addNote(n1);
        pad.selectNote(n1);
        try {
            pad.changeSelectedNoteTitle("");
            fail();
        } catch (ExistingTitleException e) {
            fail();
        } catch (Exception e) {
            // Pass
        }

        try {
            pad.changeSelectedNoteTitle("fish");
        } catch (Exception e) {
            // Passing
        }

        assertEquals("fish", pad.getSelectedNote().getNoteTitle());
    }

    @Test
    public void changeSelectedNoteTextTest() {
        pad.addNote(n1);
        pad.selectNote(n1);
        assertEquals("", pad.getSelectedNote().getText());
        pad.changeSelectedNoteText("Testing");
        assertEquals("Testing", pad.getSelectedNote().getText());
    }

    @Test
    public void addNoteTest() {
        pad.addNote(n1);
        pad.addNote(n2);
        try {
            pad.addNote(n1);
            fail();
        } catch (ExistingTitleException e) {
            // Passing
        }

        try {
            pad.addNote(new Note("Start1"));
            fail();
        } catch (ExistingTitleException e) {
            // Pass
        }
        assertEquals(2, pad.getListOfNotes().size());
    }

    @Test
    public void removeNoteTest() {
        pad.addNote(n1);
        pad.addNote(n2);
        try {
            pad.removeNote(n3);
            fail();
        } catch (DoesNotExistException e) {
            // Pass
        } catch (Exception e) {
            fail();
        }

        pad.removeNote(n1);
        assertEquals(1, pad.getListOfNotes().size());
    }

    @Test
    public void selectNoteTest() {
        pad.addNote(n1);
        pad.addNote(n2);
        try {
            pad.selectNote(n3);
            fail();
        } catch (DoesNotExistException e) {
            // Passing
        }
        assertEquals(null, pad.getSelectedNote());
        pad.selectNote(n1);
        assertEquals(n1, pad.getSelectedNote());
        assertEquals(new Note("Start1"), pad.getSelectedNote());
        pad.selectNote(null);
        assertNull(pad.getSelectedNote());
    }


}