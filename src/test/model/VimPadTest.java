package model;

import exception.ExistingTitleException;
import exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

public class VimPadTest {
    VimPad vm;

    @BeforeEach
    public void setup() {
        vm = new VimPad();
    }

    @Test
    public void processNewPad1Test() {
        assertEquals(0, vm.getListOfPad().size());
        try {
            vm.processMain("p", "test");
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, vm.getListOfPad().size());

    }

    @Test
    public void processNewPad2Test() {
        try {
            vm.processMain("p", "test");
            vm.processMain("p", "test");
            fail();
        } catch (ExistingTitleException e) {
            //pass
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, vm.getListOfPad().size());

    }

    @Test
    public void processNewNoteTest() {
        try {
            vm.processMain("n", "test");
            fail();
        } catch (NullPointerException e) {
            //pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processNewNote2Test() {
        try {
            vm.processMain("p", "test pad");
            vm.selectPad("test pad");
            vm.processMain("n", "test note");
            vm.processMain("n", "test note");
            fail();
        } catch (ExistingTitleException e) {
            //pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processNewNotePassTest() {
        try {
            vm.processMain("p", "test pad");
            vm.selectPad("test pad");
            vm.processMain("n", "test note");
            vm.processMain("n", "test note 2");
        } catch (Exception e) {
            fail();
        }

        assertEquals(2, vm.getSelectedPad().getListOfNotes().size());
    }

    @Test
    public void processRemove1Test() {
        try {
            vm.processMain("p", "test pad");
            vm.processMain("rp", "test pad");
            assertEquals(0, vm.getListOfPad().size());
            assertNull(vm.getSelectedPad());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processRemove2Test() {
        try {
            vm.processMain("p", "test pad");
            vm.selectPad("test pad");
            vm.processMain("n", "test note");
            vm.processMain("rn", "test note");
            assertEquals(0, vm.getSelectedPad().getListOfNotes().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processModifyTest() {
        try {
            vm.processMain("p", "Test pad");
            vm.selectPad("Test pad");
            vm.processMain("n", "Test note");
            vm.selectNote("Test note");
            vm.processMain("m", "Test String");
            assertEquals("Test String", vm.getSelectedNote().getText());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSelectNoteTest() {
        try {
            vm.processMain("p", "Test pad");
            vm.selectPad("Test pad");
            vm.processMain("n", "Test note");
            assertNull(vm.getSelectedNote());
            vm.selectNote("Test note");
            assertEquals("Test note", vm.getSelectedNote().getNoteTitle());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSelectNoteFailTest() {
        try {
            vm.processMain("p", "Test pad");
            vm.selectPad("Test pad");
            vm.processMain("n", "Test note");
            assertNull(vm.getSelectedNote());
            vm.selectNote("Test note1");
            fail();
        } catch (NotFoundException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSelectPad1Test() {
        try {
            vm.processMain("p", "Test pad");
            assertNull(vm.getSelectedPad());
            vm.selectPad("Test pad");
            assertEquals("Test pad", vm.getSelectedPad().getPadTitle());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSelectPadFailTest() {
        try {
            vm.processMain("p", "Test pad");
            assertNull(vm.getSelectedPad());
            vm.selectPad("Test pad1");
            fail();
        } catch (NotFoundException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSave1Test() {
        try {
            vm.processMain("p", "\0fail");
            vm.selectPad("\0fail");
            vm.processMain("s", "");
        } catch (IOException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processSave2Test() {
        try {
            vm.processMain("p", "TESTPAD1");
            vm.selectPad("TESTPAD1");
            vm.processMain("s", "");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processLoadFailTest() {
        try {
            vm.processMain("l", "fail");
            fail();
        } catch (NoSuchFileException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processLoadPassTest() {
        try {
            vm.processMain("l", "TESTPAD1");
            assertEquals(1, vm.getListOfPad().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangePadTitleTest() {
        try {
            vm.processMain("p", "Test");
            vm.selectPad("Test");
            vm.processMain("cp", "Test Pad");
            assertEquals("Test Pad", vm.getSelectedPad().getPadTitle());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangeNoteTitleTest() {
        try {
            vm.processMain("p", "Test Pad");
            vm.selectPad("Test Pad");
            vm.processMain("n", "Test");
            vm.selectNote("Test");
            vm.processMain("cn", "Test Note");
            assertEquals("Test Note", vm.getSelectedNote().getNoteTitle());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangeNoteTitleIllegalTest() {
        try {
            vm.processMain("p", "Test Pad");
            vm.selectPad("Test Pad");
            vm.processMain("n", "Test");
            vm.selectNote("Test");
            vm.processMain("cn", "");
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangeAlreadyNoteTest() {
        try {
            vm.processMain("p","Test Pad" );
            vm.selectPad("Test Pad");
            vm.processMain("n","Test");
            vm.selectNote("Test");
            vm.processMain("n" , "Test Note");
            vm.processMain("cn", "Test Note");
            fail();
        } catch (ExistingTitleException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangeAlreadyPadTest() {
        try {
            vm.processMain("p","Test Pad" );
            vm.selectPad("Test Pad");
            vm.processMain("p", "Test");
            vm.processMain("cp", "Test");
            fail();
        } catch (ExistingTitleException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processChangeAlreadyBothTest() {
        try {
            vm.processMain("p","Test" );
            vm.selectPad("Test");
            vm.processMain("n","Test");
            vm.processMain("n", "Test 1");
            vm.selectNote("Test 1");
            vm.processMain("cn", "Test");
            fail();
        } catch (ExistingTitleException e) {
            // pass
        } catch (Exception e) {
            fail();
        }
    }
}
