package rge.ui.listener;

import rge.ui.component.Panel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CursorListener extends MouseAdapter {
    private static final Cursor TEXT_CURSOR = new Cursor(Cursor.TEXT_CURSOR);
    private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    private Panel panel;

    public CursorListener(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (panel.getSearchBox().contains(e.getPoint())) {
            panel.setCursor(TEXT_CURSOR);
        } else {
            panel.setCursor(DEFAULT_CURSOR);
        }
    }
}
