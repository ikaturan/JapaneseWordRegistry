package persistence;

import model.Word;
import model.WordList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WordList wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWordList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWordList.json");
        try {
            WordList wl = reader.read();
            assertEquals("favourites", wl.getName());
            assertEquals(0, wl.countWords());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWordList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWordList.json");
        try {
            WordList wl = reader.read();
            assertEquals("favourites", wl.getName());
            List<Word> words = wl.getWords();
            assertEquals(4, words.size());
            checkWord("jisho", "dictionary", "images/jisho.png", words.get(0));
            checkWord("kamen", "mask", "images/kamen.png", words.get(1));
            checkWord("kaze", "wind", "images/kaze.png", words.get(2));
            checkWord("tokei", "clock", "images/tokei.png", words.get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
