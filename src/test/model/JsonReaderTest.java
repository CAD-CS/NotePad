package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Attribution[1]: JsonReaderTest was modelled off of the JsonReaderTest in "JsonSerializationDemo"
public class JsonReaderTest {
    @Test
    public void invalidJsonReaderTest() {
        JsonReader read = new JsonReader("./data/fail.json");
        try {
            Pad p = read.read();
            fail("Test failed");
        } catch (IOException e) {
            // nothing
        }
    }

    @Test
    public void emptyJsonReaderTest() {
        JsonReader read = new JsonReader("./data/testReadEmpty.json");
        try {
            Pad p = read.read();
            assertEquals("Test Pad" , p.getPadTitle());
            assertEquals(0,  p.getListOfNotes().size());
        } catch (IOException e) {
            fail("Test failed");        }

    }

    @Test
    public void fullJsonReaderTest() {
        JsonReader read = new JsonReader("./data/testReadFull.json");
        try {
            Pad p = read.read();
            assertEquals("Test Pad" , p.getPadTitle());
            assertEquals("Test Note",  p.getListOfNotes().get(0).getNoteTitle());
            assertEquals("Success!",  p.getListOfNotes().get(0).getText());
        } catch (IOException e) {
            fail("Test failed");        }

    }
}
