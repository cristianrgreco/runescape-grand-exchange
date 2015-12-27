package rge;

import rge.ui.component.Panel;
import rge.ui.component.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        createAndShowGui();
    }

    private static void createAndShowGui() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            new Window(new Panel());
        });
    }
}
