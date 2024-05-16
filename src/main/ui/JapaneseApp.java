package ui;

import model.Word;
import model.WordList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Citation: Referenced code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Japanese application
public class JapaneseApp {
    private static final String JSON_STORE = "./data/wordlist.json";
    private Word jisho;
    private Word kamen;
    private WordList favourites;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the Japanese application
    public JapaneseApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runJapanese();
    }


    // MODIFIES: this
    // EFFECTS: process user input
    private void runJapanese() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("7")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            addAWord();
        } else if (command.equals("2")) {
            removeAWord();
        } else if (command.equals("3")) {
            seeWordList();
        } else if (command.equals("4")) {
            seeWordCount();
        } else if (command.equals("5")) {
            saveWordList();
        } else if (command.equals("6")) {
            loadWordList();
        } else {
            searchAWord(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes words and favourites word list
    private void init() {
        jisho = new Word("jisho", "dictionary", "images/jisho.png");
        kamen = new Word("kamen", "mask", "images/kamen.png");
        favourites = new WordList("favourites");
        favourites.addWord(jisho);
        favourites.addWord(kamen);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add a new Japanese word to favourites word list");
        System.out.println("\t2 -> remove an existing Japanese word from favourites word list");
        System.out.println("\t3 -> see all words in favourites word list");
        System.out.println("\t4 -> count number of words in favourites word list");
        System.out.println("\t5 -> save word list to file");
        System.out.println("\t6 -> load word list from file");
        System.out.println("\t7 -> quit the application");
        System.out.println("\nOr type a Japanese word in romaji to search in the list");
    }

    // REQUIRES: romaji and definition input must be lowercase letters from a-z,
    //           image path input must be in format of "images/(romaji).png"
    // MODIFIES: this
    // EFFECTS: adds a word to the favourites word list
    private void addAWord() {
        Word newWord = new Word("", "", "");

        System.out.println("Enter a Japanese word in romaji:");
        String romaji = input.next();
        newWord.setRomaji(romaji);

        System.out.println("Enter its definition:");
        String definition = input.next();
        newWord.setDefinition(definition);

        System.out.println("Enter its image path:");
        String path = input.next();
        newWord.setPath(path);

        favourites.addWord(newWord);
        printWordDefPath(newWord);
        System.out.println("Word added successfully");
    }

    // MODIFIES: this
    // EFFECTS: remove a word from favourites word list
    private void removeAWord() {
        System.out.println("Enter a word to remove from the list:");
        String romaji = input.next();
        System.out.println(favourites.removeWord(romaji));
    }

    // MODIFIES: this
    // EFFECTS: print words from favourites word list
    private void seeWordList() {
        for (Word word : favourites.getWords()) {
            System.out.println(word.getRomaji());
        }
    }

    // MODIFIES: this
    // EFFECTS: show count of words in favourites word list
    private void seeWordCount() {
        System.out.println("The number of words in the list is: " + favourites.countWords());
    }

    // MODIFIES: this
    // EFFECTS: search the given word in the favourites words list
    private void searchAWord(String searchRomaji) {
        System.out.println(favourites.searchWord(searchRomaji));
    }

    // EFFECTS: print romaji, definition and image path of word
    private void printWordDefPath(Word word) {
        System.out.println(word.getRomaji() + "\n" + word.getDefinition() + "\n" + word.getPath());
    }

    // EFFECTS: saves the wordlist to file
    private void saveWordList() {
        try {
            jsonWriter.open();
            jsonWriter.write(favourites);
            jsonWriter.close();
            System.out.println("Saved " + favourites.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wordlist from file
    private void loadWordList() {
        try {
            favourites = jsonReader.read();
            System.out.println("Loaded " + favourites.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
