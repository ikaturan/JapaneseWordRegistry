package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordListTest {
    private WordList testWordList;
    private Word testWord1;
    private Word testWord2;
    private ArrayList<Word> testList;

    @BeforeEach
    void runBefore() {
        testWordList = new WordList("favourites");
        testWord1 = new Word("tokei", "clock", "image/tokei.png");
        testWord2 = new Word("kaze", "wind", "image/kaze.png");
        testList = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertTrue(testWordList.isEmpty());
    }

    @Test
    void testAddWord() {
        testWordList.addWord(testWord1);
        assertEquals(testWord1, testWordList.getWordFromList(0));
    }

    @Test
    void testAddMultipleWords() {
        testWordList.addWord(testWord1);
        testWordList.addWord(testWord2);
        assertEquals(testWord1, testWordList.getWordFromList(0));
        assertEquals(testWord2, testWordList.getWordFromList(1));
    }

    @Test
    void testSearchWord() {
        assertEquals("Word not found in list", testWordList.searchWord("jisho"));
        testWordList.addWord(testWord1);
        testWordList.addWord(testWord2);
        assertEquals("\nkaze\nwind\nimage/kaze.png",
                testWordList.searchWord("kaze"));
        assertEquals("\ntokei\nclock\nimage/tokei.png",
                testWordList.searchWord("tokei"));
    }

    @Test
    void testRemoveWord() {
        assertEquals("Word not found in list", testWordList.removeWord("jisho"));
        testWordList.addWord(testWord1);
        testWordList.addWord(testWord2);
        assertEquals("Word has been removed", testWordList.removeWord("kaze"));
    }

    @Test
    void testRemoveMultipleWords() {
        assertEquals("Word not found in list", testWordList.removeWord("kamen"));
        testWordList.addWord(testWord1);
        testWordList.addWord(testWord2);
        assertEquals("Word has been removed", testWordList.removeWord("tokei"));
        assertEquals("Word has been removed", testWordList.removeWord("kaze"));
    }

    @Test
    void testGetWords() {
        assertEquals(testList, testWordList.getWords());
        testWordList.addWord(testWord1);
        testList.add(testWord1);
        assertEquals(testList, testWordList.getWords());
        testWordList.addWord(testWord2);
        testList.add(testWord2);
        assertEquals(testList, testWordList.getWords());
    }

    @Test
    void testCountWords() {
        assertEquals(0, testWordList.countWords());
        testWordList.addWord(testWord1);
        assertEquals(1, testWordList.countWords());
        testWordList.addWord(testWord2);
        assertEquals(2, testWordList.countWords());
    }

}
