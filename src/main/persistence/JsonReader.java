package persistence;

import model.Word;
import model.WordList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads wordlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads wordlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WordList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWordList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wordlist from JSON object and returns it
    private WordList parseWordList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WordList wl = new WordList(name);
        addWords(wl, jsonObject);
        return wl;
    }

    // MODIFIES: wl
    // EFFECTS: parses words from JSON object and adds them to wordlist
    private void addWords(WordList wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("words");
        for (Object json : jsonArray) {
            JSONObject nextWord = (JSONObject) json;
            addWord(wl, nextWord);
        }
    }

    // MODIFIES: wl
    // EFFECTS: parses word from JSON object and adds it to wordlist
    private void addWord(WordList wl, JSONObject jsonObject) {
        String romaji = jsonObject.getString("romaji");
        String definition = jsonObject.getString("definition");
        String path = jsonObject.getString("path");
        Word word = new Word(romaji, definition, path);
        wl.addWord(word);
    }

}
