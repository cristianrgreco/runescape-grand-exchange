package rge.ui.listener;

import rge.engine.GrandExchange;
import rge.engine.Item;
import rge.ui.component.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SearchControls extends KeyAdapter {
    private static final int ENTER_CHARACTER_CODE = 10;
    private static final int BACKSPACE_CHARACTER_CODE = 8;

    private Panel panel;

    public SearchControls(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);

        char keyChar = e.getKeyChar();

        synchronized (SearchControls.class) {
            if (isEnterCharacter(e) && panel.getSearchText().length() > 0) {
                panel.setSearchResult(search());
                panel.repaint();
            } else if (isValidCharacter(keyChar)) {
                panel.getSearchText().append(keyChar);
                panel.repaint();
            } else if (isBackspaceCharacter(e) && panel.getSearchText().length() > 0) {
                panel.getSearchText().deleteCharAt(panel.getSearchText().length() - 1);
                panel.repaint();
            }
        }
    }

    private Item search() {
        try {
            return GrandExchange.get(panel.getSearchText().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEnterCharacter(KeyEvent e) {
        return (int) e.getKeyChar() == ENTER_CHARACTER_CODE;
    }

    private boolean isValidCharacter(char keyChar) {
        return Character.isAlphabetic(keyChar) || Character.isDigit(keyChar) || Character.isSpaceChar(keyChar);
    }

    private boolean isBackspaceCharacter(KeyEvent e) {
        return (int) e.getKeyChar() == BACKSPACE_CHARACTER_CODE;
    }
}
