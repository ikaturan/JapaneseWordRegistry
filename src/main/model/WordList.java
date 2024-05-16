package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a list of Japanese words
public class WordList implements Writable {
    private String name;
    private ArrayList<Word> words;

    // EFFECTS: create wordlist with name and a blank list of words
    public WordList(String name) {
        this.name = name;
        this.words = new ArrayList<>();
    }

    // EFFECTS: return name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: add word to words
    public void addWord(Word newWord) {
        EventLog.getInstance().logEvent(new Event("Added " + newWord.getRomaji() + " to wordlist"));
        this.words.add(newWord);
    }

    // EFFECTS: returns string of romaji, definition and image path of word, or word not found
    public String searchWord(String enteredWord) {
        for (Word word : words) {
            if (enteredWord.equals(word.getRomaji())) {
                return "\n" + word.getRomaji() + "\n" + word.getDefinition() + "\n" + word.getPath();
            }
        }
        return "Word not found in list";
    }

    // MODIFIES: this
    // EFFECTS: remove word from words
    public String removeWord(String enteredWord) {
        int i = 0;
        for (Word word : words) {
            if (enteredWord.equals(word.getRomaji())) {
                EventLog.getInstance().logEvent(new Event("Removed " + word.getRomaji() + " from wordlist"));
                words.remove(words.get(i));
                return "Word has been removed";
            }
            i = i + 1;
        }
        return "Word not found in list";
    }

    // EFFECTS: return words
    public ArrayList<Word> getWords() {
        return this.words;
    }

    // EFFECTS: return count of number of elements in words
    public int countWords() {
        EventLog.getInstance().logEvent(new Event("Counted words in wordlist"));
        return this.words.size();
    }

    // EFFECTS: return true if words is empty, else false
    public boolean isEmpty() {
        return this.words.isEmpty();
    }

    //EFFECTS: get word from words at specified index
    public Word getWordFromList(int wordIndex) {
        return this.words.get(wordIndex);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("words", wordsToJson());
        return json;
    }

    // EFFECTS: returns words in this wordlist as a JSON array
    private JSONArray wordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Word w : words) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
