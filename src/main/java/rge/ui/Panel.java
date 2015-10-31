package rge.ui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Panel extends JPanel {
    private static final BufferedImage WINDOW_BACKGROUND;
    private static final Font CUSTOM_FONT;

    static {
        try {
            String filename = "background.png";
            URL backgroundResource = Window.class.getClassLoader().getResource(filename);
            if (backgroundResource == null) {
                throw new FileNotFoundException("File '" + filename + "' not found!");
            }
            WINDOW_BACKGROUND = ImageIO.read(new File(backgroundResource.getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            CUSTOM_FONT = Font.createFont(Font.TRUETYPE_FONT, Panel.class.getClassLoader().getResourceAsStream("font.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(CUSTOM_FONT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final int WINDOW_PADDING = 20;

    private static final int TEXT_BOX_WIDTH = Window.SIZE.width - (WINDOW_PADDING * 4);
    private static final int TEXT_BOX_HEIGHT = 30;
    private static final int TEXT_BOX_PADDING = 5;

    private StringBuilder searchText = new StringBuilder();

    public Panel() {
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                char keyChar = e.getKeyChar();

                if (isValidCharacter(keyChar)) {
                    searchText.append(keyChar);
                    repaint();
                }

                if (isBackspaceCharacter(e) && searchText.length() > 0) {
                    searchText.deleteCharAt(searchText.length() - 1);
                    repaint();
                }
            }

            private boolean isValidCharacter(char keyChar) {
                return Character.isAlphabetic(keyChar) || Character.isSpaceChar(keyChar);
            }

            private boolean isBackspaceCharacter(KeyEvent e) {
                return (int) e.getKeyChar() == 8;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBackground(g2d);
        drawContainer(g2d);
        drawSearchBox(g2d);
        drawSearchText(g2d);
    }

    private boolean drawBackground(Graphics2D g2d) {
        return g2d.drawImage(WINDOW_BACKGROUND, 0, 0, this);
    }

    private void drawContainer(Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.25f));
        g2d.fillRoundRect(WINDOW_PADDING, WINDOW_PADDING, Window.SIZE.width - (WINDOW_PADDING * 2), Window.SIZE.height - (WINDOW_PADDING * 3), 5, 5);
    }

    private void drawSearchBox(Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.9f));
        g2d.fillRoundRect(WINDOW_PADDING * 2, WINDOW_PADDING * 2, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT, 5, 5);
    }

    private void drawSearchText(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.decode("#3F3F3F"));
        g2d.setFont(CUSTOM_FONT.deriveFont((float) 40 - (TEXT_BOX_PADDING * 3)));

        String searchText = fitTextToSearchBox(g2d);
        g2d.drawString(
                searchText,
                (WINDOW_PADDING * 2) + TEXT_BOX_PADDING,
                WINDOW_PADDING * 2 + TEXT_BOX_HEIGHT - TEXT_BOX_PADDING);
    }

    private String fitTextToSearchBox(Graphics2D g2d) {
        int textBoxBounds = TEXT_BOX_WIDTH - (TEXT_BOX_PADDING * 3);

        String searchText = this.searchText.toString();
        while (g2d.getFontMetrics().stringWidth(searchText) > textBoxBounds) {
            searchText = searchText.substring(1);
        }

        return searchText;
    }
}
