package rge.ui.listener;

import rge.engine.GrandExchange;
import rge.engine.Item;
import rge.ui.component.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SearchControls extends KeyAdapter {
    public static final int KEY_REPEAT_RATE = 500;

    private Panel panel;

    public SearchControls(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyTyped(e);

        synchronized (SearchControls.class) {
            if (isEnterCharacter(e) && panel.getSearchText().length() > 0) {
                panel.setSearchResult(search());
                panel.repaint();
            } else if (isValidCharacter(e.getKeyChar())) {
                int positionAtCursor = panel.getSearchText().length() - panel.getCursorTask().getCursorIndex();
                panel.getSearchText().insert(positionAtCursor, e.getKeyChar());
                panel.repaint();
            } else if (isBackspaceCharacter(e) && panel.getSearchText().length() > 0) {
                int positionAtCursor = panel.getSearchText().length() - 1 - panel.getCursorTask().getCursorIndex();
                panel.getSearchText().deleteCharAt(positionAtCursor);
                panel.repaint();
            } else if (isLeftArrowKey(e)) {
                panel.getCursorTask().moveCursorLeft();
                panel.repaint();
            } else if (isRightArrowKey(e)) {
                panel.getCursorTask().moveCursorRight();
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
        return e.getKeyCode() == KeyEvent.VK_ENTER;
    }

    private boolean isValidCharacter(char keyChar) {
        return Character.isAlphabetic(keyChar) || Character.isDigit(keyChar) || Character.isSpaceChar(keyChar);
    }

    private boolean isBackspaceCharacter(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_BACK_SPACE;
    }

    private boolean isLeftArrowKey(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_LEFT;
    }

    private boolean isRightArrowKey(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_RIGHT;
    }
}
