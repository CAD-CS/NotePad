package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.ExistingTitleException;

public class PadManagerTest {

    @BeforeEach
    public void setup() {
        PadManager.getInstance();
    }

    @Test
    public void newPadTest() {
        try {
            assertEquals(0, PadManager.getInstance().getListOfPad().size());
            PadManager.getInstance().newPad("Test Pad");
            assertEquals(1,PadManager.getInstance().getListOfPad().size());
        } catch (Exception e) {
            fail();
        }
        try {
            PadManager.getInstance().newPad("Test Pad");
            fail();
        } catch (ExistingTitleException e) {
            // Pass
        }
    }

    @Test
    public void removePadTest() {
        try {
            PadManager.getInstance().newPad("Test Pad");
            PadManager.getInstance().newPad("Test Pad 2");
            assertEquals(2, PadManager.getInstance().getListOfPad().size());
            PadManager.getInstance().selectPad("Test Pad");
            PadManager.getInstance().removePad("Test Pad");
            assertNull(PadManager.getInstance().getSelectedPad());
            assertEquals(1, PadManager.getInstance().getListOfPad().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test 
    public void changePadTitleTest() {
        try {
            PadManager.getInstance().newPad("Test Pad");
            PadManager.getInstance().newPad("Test Pad 2");
            PadManager.getInstance().selectPad("Test Pad");
            PadManager.getInstance().changePadTitle("Test Pad 2");
            fail();
        } catch (Exception e) {
            // Pass
        }
    }
}
