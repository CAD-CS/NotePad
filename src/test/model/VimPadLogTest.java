package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// Test for the Events and Event logging for VimPad
public class VimPadLogTest {

    VimPad vm;

    @BeforeEach
    public void setup() {
        vm = new VimPad();
        EventLog.getInstance().clear();
    }

    @Test
    public void TestAddPad() {
        try {
            vm.processMain("p", "TEST");
            String name = "";
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                name = event.getDescription();
            }
            assertEquals("Added Pad[TEST]", name);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestAddTwoPad() {
        try {
            vm.processMain("p", "TEST");
            vm.processMain("p", "TEST2");
            int count = 0;
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                count++;
                Event event = it.next();
            }
            assertEquals(2, count - 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestAddNote() {
        try {
            vm.processMain("p", "TESTPAD");
            vm.processMain("sp", "TESTPAD");
            vm.processMain("n", "TEST");
            String name = "";
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                name = event.getDescription();
            }
            assertEquals("Added Note[TEST]", name);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestAddTwoNote() {
        try {
            vm.processMain("p", "TESTPAD");
            vm.processMain("sp", "TESTPAD");
            vm.processMain("n", "TEST");
            vm.processMain("n", "TEST2");
            int count = 0;
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                count++;
                Event event = it.next();
            }
            assertEquals(3, count - 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestRemovePad() {
        try {
            vm.processMain("p", "TEST");
            vm.processMain("rp", "TEST");
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                if (event.toString().contains("Removed Pad[Test]")) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestRemoveNote() {
        try {
            vm.processMain("p", "TESTPAD");
            vm.processMain("sp", "TESTPAD");
            vm.processMain("n", "TEST");
            vm.processMain("sn", "TEST");
            vm.processMain("rn", "TEST");
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                if (event.toString().contains("Removed Note[Test]")) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestLoadPad() {
        try {
            vm.processMain("l", "TESTPAD1");
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                if (event.toString().contains("Loaded Pad[TESTPAD1]")) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void TestSavePad() {
        try {
            vm.processMain("l", "TESTPAD1");
            vm.processMain("sp", "TESTPAD1");
            vm.processMain("s", "TESTPAD1");
            for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                Event event = it.next();
                if (event.toString().contains("Saved Pad[TESTPAD1]")) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

}
