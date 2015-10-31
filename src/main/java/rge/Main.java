package rge;

import rge.ui.Panel;
import rge.ui.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new Window(new Panel());
        });
    }
}
