package rge.ui.task;

import rge.ui.component.Panel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cursor extends TimerTask {
    private static final int KEY_REPEAT_RATE = 500;

    private Panel panel;

    private AtomicBoolean cursorVisible = new AtomicBoolean(false);

    private long lastTimeCheck = System.currentTimeMillis();
    private String lastSearchCheck = "";

    public Cursor(Panel panel) {
        this.panel = panel;
        new Timer().scheduleAtFixedRate(this, 0, KEY_REPEAT_RATE);
    }

    @Override
    public void run() {
        String newSearchCheck = panel.getSearchText().toString();
        long newTimeCheck = System.currentTimeMillis();

        if (userIsEnteringText(newSearchCheck, newTimeCheck)) {
            lastSearchCheck = newSearchCheck;
            cursorVisible.set(true);
        } else {
            cursorVisible.set(!cursorVisible.get());
        }
        lastTimeCheck = newTimeCheck;

        panel.repaint();
    }

    private boolean userIsEnteringText(String newSearchCheck, long newTimeCheck) {
        return !lastSearchCheck.equals(newSearchCheck) && newTimeCheck - lastTimeCheck < (KEY_REPEAT_RATE * 2);
    }

    public boolean isCursorVisible() {
        return cursorVisible.get();
    }
}
