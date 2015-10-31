package rge;

import rge.ui.component.Panel;
import rge.ui.component.Window;

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
