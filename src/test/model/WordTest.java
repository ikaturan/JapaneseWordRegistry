package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    private Word testWord1;
    private Word testWord2;
    private Word blankWord;

    @BeforeEach
    void runBefore() {
        testWord1 = new Word("watashi", "me", "images/watashi.png");
        blankWord = new Word("", "", "");
    }

    @Test
    void testConstructor() {
        testWord2 = new Word("watashi", "me", "images/watashi.png");
        assertEquals(testWord2.getRomaji(), testWord1.getRomaji());
        assertEquals(testWord2.getDefinition(), testWord1.getDefinition());
        assertEquals(testWord2.getPath(), testWord1.getPath());
    }

    @Test
    void testSetRomaji() {
        blankWord.setRomaji("watashi");
        assertEquals("watashi", blankWord.getRomaji());
    }

    @Test
    void testSetDefinition() {
        blankWord.setDefinition("me");
        assertEquals("me", blankWord.getDefinition());
    }

    @Test
    void testSetPath() {
        blankWord.setPath("images/watashi.png");
        assertEquals("images/watashi.png", blankWord.getPath());
    }

}
