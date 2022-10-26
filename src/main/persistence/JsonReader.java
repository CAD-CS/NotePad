package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Note;
import model.Pad;
import org.json.*;


// Attribution[1]: JsonReader class was modelled after JsonReader in "JsonSerializationDemo" given in the instructions
// Represents a reader that reads a Pad from a JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a Pad from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Pad read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePad(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses a pad from JSON object and returns it
    private Pad parsePad(JSONObject jsonObject) {
        String name = jsonObject.getString("padTitle");
        Pad p = new Pad(name);
        addNotes(p, jsonObject);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses notes from JSON object and adds them to pad
    private void addNotes(Pad p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfNotes");
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(p, nextNote);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses notes from JSON object and adds it to pad
    private void addNote(Pad p, JSONObject jsonObject) {
        String title = jsonObject.getString("noteTitle");
        String text = jsonObject.getString("text");
        Note n = new Note(title);
        n.changeNoteText(text);
        p.addNote(n);
    }
}
