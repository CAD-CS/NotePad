package persistence;

import org.json.JSONObject;

// Attribution[1]: Writable interface was modelled after JsonWriter in "JsonSerializationDemo" given in the instructions
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
