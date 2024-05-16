package model;

import org.json.JSONObject;
import persistence.Writable;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a Japanese word with its romaji, definition and image path
public class Word implements Writable {
    private String romaji;
    private String definition;
    private String path;

    // REQUIRES: romaji and definition must be a-z characters in lowercase
    //           image path in format of "images/(romaji).png"
    public Word(String romaji, String definition, String path) {
        this.romaji = romaji;
        this.definition = definition;
        this.path = path;
    }

    // EFFECTS: return this.romaji
    public String getRomaji() {
        return this.romaji;
    }

    // EFFECTS: return this.definition
    public String getDefinition() {
        return this.definition;
    }

    // EFFECTS: return this.path
    public String getPath() {
        return this.path;
    }

    // MODIFIES: this
    // EFFECTS: set this.romaji to newRomaji
    public void setRomaji(String newRomaji) {
        this.romaji = newRomaji;
    }

    // MODIFIES: this
    // EFFECTS: set this.definition to newDefinition
    public void setDefinition(String newDefinition) {
        this.definition = newDefinition;
    }

    // MODIFIES: this
    // EFFECTS: set this.path to newPath
    public void setPath(String newPath) {
        this.path = newPath;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("romaji", romaji);
        json.put("definition", definition);
        json.put("path", path);
        return json;
    }
}
