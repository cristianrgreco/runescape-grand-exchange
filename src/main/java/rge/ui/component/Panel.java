package rge.ui.component;

import rge.engine.GrandExchange;
import rge.engine.Item;
import rge.ui.listener.CursorListener;
import rge.ui.listener.SearchControls;
import rge.ui.task.Cursor;
import rge.util.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;

public class Panel extends JPanel {
    private static final Image WINDOW_BACKGROUND = ResourceLoader.image("background.png", Panel.class);
    private static final Font CUSTOM_FONT = ResourceLoader.font("font.ttf", Panel.class);

    private static final Color TEXT_COLOUR = Color.decode("#3F3F3F");

    private static final int WINDOW_PADDING = 20;
    private static final int ITEM_IMAGE_CONTAINER_PADDING = 4;

    private static final int TEXT_BOX_WIDTH = Window.SIZE.width - (WINDOW_PADDING * 4);
    private static final int TEXT_BOX_HEIGHT = 30;
    private static final int TEXT_BOX_Y_PADDING = 5;
    private static final int TEXT_BOX_X_PADDING = 15;
    private static final Rectangle TEXT_BOX = new Rectangle(WINDOW_PADDING * 2, WINDOW_PADDING * 2, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);

    private Cursor cursorTask;
    private StringBuilder searchText = new StringBuilder();
    private Item searchResult;

    public Panel() {
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(new SearchControls(this));
        addMouseMotionListener(new CursorListener(this));
        cursorTask = new Cursor(this);
    }

    public StringBuilder getSearchText() {
        return searchText;
    }

    public void setSearchResult(Item searchResult) {
        this.searchResult = searchResult;
    }

    public Rectangle getSearchBox() {
        return TEXT_BOX;
    }

    public Cursor getCursorTask() {
        return cursorTask;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBackground(g2d);
        drawContainer(g2d);
        drawSearchBox(g2d);
        drawSearchText(g2d);
        if (cursorTask.isCursorVisible()) {
            drawSearchBoxCursor(g2d);
        }

        if (searchResult != null) {
            drawItemContainer(g2d);
            drawItemName(g2d);
            if (searchResult.price != null) {
                drawItemPrice(g2d);
            }
            if (searchResult.imageUrl != null) {
                drawItemImage(g2d);
            }
        }
    }

    private boolean drawBackground(Graphics2D g2d) {
        return g2d.drawImage(WINDOW_BACKGROUND, 0, 0, this);
    }

    private void drawContainer(Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.25f));
        g2d.fillRoundRect(
                WINDOW_PADDING,
                WINDOW_PADDING,
                Window.SIZE.width - (WINDOW_PADDING * 2),
                Window.SIZE.height - (WINDOW_PADDING * 3), 5, 5);
    }

    private void drawSearchBox(Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.9f));
        g2d.fillRoundRect(TEXT_BOX.x, TEXT_BOX.y, TEXT_BOX.width, TEXT_BOX.height, 5, 5);
    }

    private void drawSearchText(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(TEXT_COLOUR);
        g2d.setFont(CUSTOM_FONT.deriveFont((float) 40 - (TEXT_BOX_Y_PADDING * 3)));

        String searchText = fitTextToSearchBox(this.searchText.toString().toUpperCase(), g2d);
        g2d.drawString(
                searchText.toUpperCase(),
                (WINDOW_PADDING * 2) + TEXT_BOX_X_PADDING,
                WINDOW_PADDING * 2 + TEXT_BOX_HEIGHT - (int) (TEXT_BOX_Y_PADDING * 1.5));
    }

    private static String fitTextToSearchBox(String searchText, Graphics2D g2d) {
        int textBoxBounds = TEXT_BOX_WIDTH - (TEXT_BOX_X_PADDING * 2);

        while (g2d.getFontMetrics().stringWidth(searchText) > textBoxBounds) {
            searchText = searchText.substring(1);
        }

        return searchText;
    }

    private void drawSearchBoxCursor(Graphics2D g2d) {
        String searchText = this.searchText.toString().toUpperCase();
        String searchTextSubstringToCursor = searchText.substring(0, searchText.length() - cursorTask.getCursorIndex());
        int stringWidth = g2d.getFontMetrics().stringWidth(fitTextToSearchBox(searchTextSubstringToCursor, g2d));

        g2d.setColor(TEXT_COLOUR);
        g2d.drawLine(
                TEXT_BOX.x + TEXT_BOX_X_PADDING + stringWidth,
                TEXT_BOX.y + TEXT_BOX_Y_PADDING,
                TEXT_BOX.x + TEXT_BOX_X_PADDING + stringWidth,
                TEXT_BOX.y + TEXT_BOX.height - TEXT_BOX_Y_PADDING);
    }

    private void drawItemContainer(Graphics2D g2d) {
        g2d.setColor(new Color(0f, 0f, 0f, 0.5f));
        g2d.fillRoundRect(WINDOW_PADDING * 2, WINDOW_PADDING * 4, TEXT_BOX_WIDTH, Window.SIZE.height - (WINDOW_PADDING * 7), 5, 5);
    }

    private void drawItemName(Graphics2D g2d) {
        if (GrandExchange.NOT_FOUND_TEXT.equals(searchResult.name)) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.YELLOW);
        }
        g2d.setFont(CUSTOM_FONT.deriveFont((float) 16));

        g2d.drawString(
                searchResult.name,
                WINDOW_PADDING * 3,
                (WINDOW_PADDING * 3) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2) + ITEM_IMAGE_CONTAINER_PADDING);
    }

    private void drawItemPrice(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(CUSTOM_FONT.deriveFont((float) 16));

        g2d.drawString(
                searchResult.price + (searchResult.price != null ? " coins" : ""),
                WINDOW_PADDING * 3,
                (WINDOW_PADDING * 4) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2));
    }

    private void drawItemImage(Graphics2D g2d) {
        Image itemImage = downloadItemImage();

        drawItemImageContainer(itemImage, g2d);

        int containerHeight = Window.SIZE.height - (WINDOW_PADDING * 7);
        int imageHeight = itemImage.getHeight(this);
        int topOfContainer = WINDOW_PADDING * 4;
        int middleOfContainer = (containerHeight - imageHeight) / 2;

        g2d.drawImage(
                itemImage,
                Window.SIZE.width - (WINDOW_PADDING * 3) - itemImage.getWidth(this) + (ITEM_IMAGE_CONTAINER_PADDING / 2),
                (ITEM_IMAGE_CONTAINER_PADDING / 2) + topOfContainer + middleOfContainer - (ITEM_IMAGE_CONTAINER_PADDING / 2),
                this);
    }

    private void drawItemImageContainer(Image itemImage, Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.9f));

        int containerHeight = Window.SIZE.height - (WINDOW_PADDING * 7);
        int imageHeight = itemImage.getHeight(this);
        int topOfContainer = WINDOW_PADDING * 4;
        int middleOfContainer = (containerHeight - imageHeight) / 2;

        g2d.fillRoundRect(
                Window.SIZE.width - (WINDOW_PADDING * 3) - itemImage.getWidth(this),
                topOfContainer + middleOfContainer - (ITEM_IMAGE_CONTAINER_PADDING / 2),
                itemImage.getWidth(this) + ITEM_IMAGE_CONTAINER_PADDING,
                itemImage.getHeight(this) + ITEM_IMAGE_CONTAINER_PADDING, 5, 5);
    }

    private Image downloadItemImage() {
        try {
            return ImageIO.read(new URL(searchResult.imageUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
