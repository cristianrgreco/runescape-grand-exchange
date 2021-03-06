package rge.ui.task;

import rge.ui.component.Panel;
import rge.ui.listener.SearchControls;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cursor extends TimerTask {
    private Panel panel;

    private int cursorIndex = 0;
    private AtomicBoolean cursorVisible = new AtomicBoolean(false);

    private long lastTimeCheck = System.currentTimeMillis();
    private String lastSearchCheck = "";
    private int lastCursorIndexCheck = getCursorIndex();

    public Cursor(Panel panel) {
        this.panel = panel;
        new Timer().scheduleAtFixedRate(this, 0, SearchControls.KEY_REPEAT_RATE);
    }

    @Override
    public void run() {
        long newTimeCheck = System.currentTimeMillis();
        String newSearchCheck = panel.getSearchText().toString();
        int newCursorIndexCheck = panel.getCursorTask().getCursorIndex();

        if (userIsEnteringText(newSearchCheck, newTimeCheck) || userIsMovingCursor(newCursorIndexCheck, newTimeCheck)) {
            lastSearchCheck = newSearchCheck;
            lastCursorIndexCheck = newCursorIndexCheck;
            cursorVisible.set(true);
        } else {
            cursorVisible.set(!cursorVisible.get());
        }
        lastTimeCheck = newTimeCheck;

        panel.repaint();
    }

    private boolean userIsEnteringText(String newSearchCheck, long newTimeCheck) {
        return !lastSearchCheck.equals(newSearchCheck) && isWithinTimeConstraints(newTimeCheck);
    }

    private boolean userIsMovingCursor(int newCursorIndexCheck, long newTimeCheck) {
        return lastCursorIndexCheck != newCursorIndexCheck && isWithinTimeConstraints(newTimeCheck);
    }

    private boolean isWithinTimeConstraints(long newTimeCheck) {
        return newTimeCheck - lastTimeCheck < (SearchControls.KEY_REPEAT_RATE * 2);
    }

    public boolean isCursorVisible() {
        return cursorVisible.get();
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void moveCursorLeft() {
        if (cursorIndex >= 0 && cursorIndex < panel.getSearchText().length()) {
            cursorIndex++;
        }
    }

    public void moveCursorRight() {
        if (cursorIndex > 0) {
            cursorIndex--;
        }
    }
}
