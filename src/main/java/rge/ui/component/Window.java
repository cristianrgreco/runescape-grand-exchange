package rge.ui.component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class Window extends JFrame {
    static final Dimension SIZE = new Dimension(320, 195);

    public Window(Panel panel) {
        setTitle("Runescape - Grand Exchange");

        setPreferredSize(SIZE);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(panel);
        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }
}
