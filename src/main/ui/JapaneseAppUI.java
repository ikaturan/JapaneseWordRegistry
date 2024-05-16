package ui;

import model.Word;
import model.WordList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


// Represents application's main window frame

public class JapaneseAppUI extends JFrame {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private JTable table;
    private JPanel panel;
    protected WordList wl;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JInternalFrame controlPanel2;
    private JTextField romaji;
    private JTextField meaning;
    private JTextField image;
    private Word jisho;
    private Word kamen;
    private DefaultTableModel model;
    private static final String JSON_STORE = "./data/wordlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates button panel, control panel 1 with table, control panel 2 with text fields
    public JapaneseAppUI() {
        wl = new WordList("favourites");
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());

        controlPanel = new JInternalFrame("Japanese Words", false, false, false,
                false);
        controlPanel.setLayout(new BorderLayout());
        controlPanel2 = new JInternalFrame("Enter Word", false, false, false,
                false);
        controlPanel2.setLayout(new BorderLayout());
        controlPanel2.setBounds(700, 0, 500, 500);

        addControlPanelMethods();
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        controlPanel2.pack();
        controlPanel2.setVisible(true);
        desktop.add(controlPanel2);
        setContentPane(desktop);

        this.addWindowListener(new CustomWindowListener());

        setIcon();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }

    // Custom WindowListener to handle window closing event
    private class CustomWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            // Print all logged events when the window is closing
            printLoggedEvents();
        }
    }

    // MODIFIES: this
    // EFFECTS: change image icon to Japanese flag
    public void setIcon() {
        this.setTitle("Japanese Word Registry");
        this.getContentPane().setBackground(Color.gray);
        ImageIcon image = new ImageIcon("src/main/images/Flag_of_Japan.png");
        this.setIconImage(image.getImage());
    }

    // MODIFIES: this
    // EFFECTS: add elements to control panel
    public void addControlPanelMethods() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        wl.addWord(jisho = new Word("jisho", "dictionary", "images/jisho.png"));
        wl.addWord(kamen = new Word("kamen", "mask", "images/kamen.png"));
        addWordCount();
        addImage();
        addPanel();
        addTable();
        setIcon();
    }

    // MODIFIES: this
    // EFFECTS: helper to create the table
    public void addTable() {
        model = new DefaultTableModel();
        model.addColumn("Romaji");
        model.addColumn("Meaning");
        model.addColumn("Image");

        getWordListData();

        table = new JTable(model);
        table.setBounds(30, 40, 200, 200);

        JScrollPane sp = new JScrollPane(table);
        controlPanel.add(sp, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: get the words in String[][] such that the table can use it as data
    public void getWordListData() {
        model.setRowCount(0);
        for (Word w : wl.getWords()) {
            model.addRow(new String[]{
                    w.getRomaji(), w.getDefinition(), w.getPath()
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize JTextField
    public void init() {
        romaji = new JTextField();
        this.getContentPane().add(romaji);
        meaning = new JTextField();
        this.getContentPane().add(meaning);
        image = new JTextField();
        this.getContentPane().add(image);
    }

    // MODIFIES: this
    // EFFECTS: helper to add the panel
    public void addPanel() {
        panel = new JPanel();
        init();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Romaji"));
        panel.add(romaji);
        panel.add(new JLabel("Meaning"));
        panel.add(meaning);
        panel.add(new JLabel("Image"));
        panel.add(image);
        panel.add(new JButton(new AddWord()));
        panel.add(new JButton(new RemoveWord()));
        panel.add(new JButton(new SaveData()));
        panel.add(new JButton(new ReloadData()));
        controlPanel2.add(panel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: add wordCountAction to controlPanel
    private void addWordCount() {
        WordCountAction wordCountAction = new WordCountAction(this);
        controlPanel.add(wordCountAction, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: add imageAction to controlPanel
    private void addImage() {
        ImageAction imageAction = new ImageAction(this);
        controlPanel.add(imageAction, BorderLayout.SOUTH);
    }

    // Object for adding new rows into table
    Object[] row = new Object[3];

    // Adds word to wordlist
    private class AddWord extends AbstractAction {

        AddWord() {
            super("Add");
        }

        // REQUIRES: romaji and definition input must be lowercase letters from a-z,
        //           image path input must be in format of "images/(romaji).png"
        // MODIFIES: this
        // EFFECTS: adds a word to the favourites word list
        @Override
        public void actionPerformed(ActionEvent e) {
            row[0] = romaji.getText();
            row[1] = meaning.getText();
            row[2] = "images/" + image.getText();

            model.addRow(row);
            Word newWord = new Word(romaji.getText(), meaning.getText(),
                    "images/" + image.getText());
            wl.addWord(newWord);
        }
    }

    // Removes word from wordlist
    private class RemoveWord extends AbstractAction {

        RemoveWord() {
            super("Remove");
        }

        // MODIFIES: this
        // EFFECTS: remove a word from word list
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = table.getSelectedRow();
            if (i >= 0) {
                wl.removeWord(model.getValueAt(i, 0).toString());
                model.removeRow(i);
            } else {
                JOptionPane.showMessageDialog(JapaneseAppUI.this, "Delete Error");
            }
        }
    }

    // Save the data to the Json wordlist file
    private class SaveData extends AbstractAction {

        SaveData() {
            super("Save");
        }

        // EFFECTS: saves the wordlist to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(wl);
                jsonWriter.close();
                JOptionPane.showMessageDialog(JapaneseAppUI.this,
                        "Saved " + wl.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException f) {
                JOptionPane.showMessageDialog(JapaneseAppUI.this,
                        "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Reload the data from the Json file
    private class ReloadData extends AbstractAction {

        ReloadData() {
            super("Reload");
        }

        // MODIFIES: this
        // EFFECTS: loads wordlist from file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                wl = jsonReader.read();
                getWordListData();
                JOptionPane.showMessageDialog(JapaneseAppUI.this,
                        "Loaded " + wl.getName() + " from " + JSON_STORE);
            } catch (IOException f) {
                JOptionPane.showMessageDialog(JapaneseAppUI.this,
                        "Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // Action for counting the number of words in the list
    private class WordCountAction extends JInternalFrame {
        private Component theParent;
        private JLabel countLabel;

        // EFFECTS: creates the button for the action to count words
        public WordCountAction(Component parent) {
            super("WordList", false, false, false, false);
            theParent = parent;
            JButton countWords = new JButton(new CountWords());
            countLabel = new JLabel("Number of words: " + wl.countWords());
            countWords.setSize(100, 100);
            countLabel.setSize(100, 100);
            add(countWords, BorderLayout.WEST);
            add(countLabel, BorderLayout.EAST);
            setPosition(parent);
            setVisible(true);
        }

        // EFFECTS: sets position for the frame
        private void setPosition(Component parent) {
            setLocation(parent.getWidth() - getWidth(), 0);
        }

        // Action for counting the words
        private class CountWords extends AbstractAction {
            CountWords() {
                super("Update Count");
            }

            // EFFECTS: count words and updates on GUI
            @Override
            public void actionPerformed(ActionEvent e) {
                countLabel.setText("Number of words: " + wl.countWords());
            }
        }

    }

    // Action for displaying the image in the list
    private class ImageAction extends JInternalFrame {
        private Component theParent;

        // EFFECTS: creates the button for displaying the image
        public ImageAction(Component parent) {
            super("Select Word From List", false, false, false, false);
            theParent = parent;
            JButton displayImage = new JButton(new DisplayImage());
            JButton quitApp = new JButton(new QuitApplication());
            displayImage.setSize(100, 100);
            add(displayImage, BorderLayout.WEST);
            add(quitApp, BorderLayout.EAST);
            setPosition(parent);
            setVisible(true);
        }

        // EFFECTS: sets position for the frame
        private void setPosition(Component parent) {
            setLocation(parent.getWidth() - getWidth(), 0);
        }

        // Action for displaying the image
        private class DisplayImage extends AbstractAction {
            private ImageWindowUI displayImg;

            DisplayImage() {
                super("Display Image");
            }

            // EFFECTS: opens a new window to display the image, or no image if no matching filename
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    displayImg = new ImageWindowUI(model.getValueAt(i, 2).toString());
                } else {
                    JOptionPane.showMessageDialog(JapaneseAppUI.this, "Image Not Found");
                }
            }
        }

        // Action for quitting the application
        private class QuitApplication extends AbstractAction {
            QuitApplication() {
                super("Quit");
            }

            // EFFECTS: quit the application
            @Override
            public void actionPerformed(ActionEvent e) {
                printLoggedEvents();
                System.exit(0);
            }
        }
    }

    // EFFECTS: Print all logged events
    private void printLoggedEvents() {
        System.out.println("Logged events:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDescription());
        }
    }

    // Represents action to be taken when user clicks desktop to switch focus (needed for key handling)
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JapaneseAppUI.this.requestFocusInWindow();
        }
    }

    // starts the application
    public static void main(String[] args) {
        new JapaneseAppUI();
    }

}
