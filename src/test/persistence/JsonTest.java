package persistence;

import model.Word;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkWord(String name, String definition, String path, Word word) {
        assertEquals(name, word.getRomaji());
        assertEquals(definition, word.getDefinition());
        assertEquals(path, word.getPath());
    }
}
