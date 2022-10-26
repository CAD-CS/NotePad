package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Attribution[1]: JsonWriterTest was modelled off of the JsonWriterTest in "JsonSerializationDemo"
public class JsonWriterTest {
    @Test
    public void invalidJsonWriterTest() {

        JsonWriter write = new JsonWriter("./data/\0fail.json");
        try {
            Pad p = new Pad("Test Pad");
            write.open();
            fail("Should've thrown file not found");
        } catch (IOException e) {
            // nothing
        }
    }

    @Test
    public void emptyJsonReaderTest() {
        JsonWriter write = new JsonWriter("./data/testWriteEmpty.json");
        JsonReader read = new JsonReader("./data/testWriteEmpty.json");
        try {
            Pad p = new Pad("Test Pad");
            write.open();
            write.write(p);
            write.close();

            p = read.read();
            assertEquals("Test Pad" , p.getPadTitle());
            assertEquals(0 , p.getListOfNotes().size());



        } catch (IOException e) {
            fail("Test Failed");
        }
    }

    @Test
    public void fullJsonReaderTest() {
        JsonWriter write = new JsonWriter("./data/testWriteEmpty.json");
        JsonReader read = new JsonReader("./data/testWriteEmpty.json");
        try {
            Pad p = new Pad("Test Pad");
            Note n = new Note("Test Note");
            n.changeNoteText("Success!");
            p.addNote(n);
            write.open();
            write.write(p);
            write.close();

            p = read.read();
            assertEquals("Test Pad" , p.getPadTitle());
            assertEquals(1 , p.getListOfNotes().size());
            assertEquals("Success!" , p.getListOfNotes().get(0).getText());


        } catch (IOException e) {
            fail("Test Failed");
        }
    }
}
