package persistence;

import model.Word;
import model.WordList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WordList wl = new WordList("favourites");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWordList() {
        try {
            WordList wl = new WordList("favourites");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWordList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWordList.json");
            wl = reader.read();
            assertEquals("favourites", wl.getName());
            assertEquals(0, wl.countWords());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWordList() {
        try {
            WordList wl = new WordList("favourites");
            wl.addWord(new Word("jisho", "dictionary", "images/jisho.png"));
            wl.addWord(new Word("kamen", "mask", "images/kamen.png"));
            wl.addWord(new Word("tokei", "clock", "images/tokei.png"));
            wl.addWord(new Word("watashi", "me", "images/watashi.png"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWordList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWordList.json");
            wl = reader.read();
            assertEquals("favourites", wl.getName());
            List<Word> words = wl.getWords();
            assertEquals(4, words.size());
            checkWord("jisho", "dictionary", "images/jisho.png", words.get(0));
            checkWord("kamen", "mask", "images/kamen.png", words.get(1));
            checkWord("tokei", "clock", "images/tokei.png", words.get(2));
            checkWord("watashi", "me", "images/watashi.png", words.get(3));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
