package ui;

import javax.swing.*;
import java.awt.*;

// Displays image in a new window
public class ImageWindowUI {

    JFrame frame = new JFrame();

    // EFFECTS: creates a frame with the image, or no image if there is no matching filename
    ImageWindowUI(String path) {
        frame.setTitle("Image");

        frame.setSize(200, 200);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);

        ImageIcon icon = new ImageIcon("src/main/" + path);

        JLabel label = new JLabel(icon);

        frame.add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
