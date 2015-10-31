package rge.ui.component;

import rge.engine.Item;
import rge.ui.listener.SearchControls;
import rge.util.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;

public class Panel extends JPanel {
    private static final Image WINDOW_BACKGROUND = ResourceLoader.image("background.png", Panel.class);
    private static final Font CUSTOM_FONT = ResourceLoader.font("font.ttf", Panel.class);

    private static final int WINDOW_PADDING = 20;

    private static final int TEXT_BOX_WIDTH = Window.SIZE.width - (WINDOW_PADDING * 4);
    private static final int TEXT_BOX_HEIGHT = 30;
    private static final int TEXT_BOX_Y_PADDING = 5;
    private static final int TEXT_BOX_X_PADDING = 15;

    private static final int ITEM_IMAGE_CONTAINER_PADDING = 4;

    private StringBuilder searchText = new StringBuilder();
    private Item searchResult;

    public Panel() {
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(new SearchControls(this));
    }

    public StringBuilder getSearchText() {
        return searchText;
    }

    public void setSearchResult(Item searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBackground(g2d);
        drawContainer(g2d);
        drawSearchBox(g2d);
        drawSearchText(g2d);

        if (searchResult != null) {
            drawItemContainer(g2d);
            drawItemName(g2d);
            drawItemPrice(g2d);
            drawItemImage(g2d);
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
        g2d.fillRoundRect(WINDOW_PADDING * 2, WINDOW_PADDING * 2, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT, 5, 5);
    }

    private void drawSearchText(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.decode("#3F3F3F"));
        g2d.setFont(CUSTOM_FONT.deriveFont((float) 40 - (TEXT_BOX_Y_PADDING * 3)));

        String searchText = fitTextToSearchBox(g2d);
        g2d.drawString(
                searchText,
                (WINDOW_PADDING * 2) + TEXT_BOX_X_PADDING,
                WINDOW_PADDING * 2 + TEXT_BOX_HEIGHT - TEXT_BOX_Y_PADDING);
    }

    private String fitTextToSearchBox(Graphics2D g2d) {
        int textBoxBounds = TEXT_BOX_WIDTH - (int) (TEXT_BOX_X_PADDING * 1.5);

        String searchText = this.searchText.toString();
        while (g2d.getFontMetrics().stringWidth(searchText) > textBoxBounds) {
            searchText = searchText.substring(1);
        }

        return searchText;
    }

    private void drawItemContainer(Graphics2D g2d) {
        g2d.setColor(new Color(0f, 0f, 0f, 0.5f));
        g2d.fillRoundRect(WINDOW_PADDING * 2, WINDOW_PADDING * 4, TEXT_BOX_WIDTH, Window.SIZE.height - (WINDOW_PADDING * 7), 5, 5);
    }

    private void drawItemName(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("sans-serif", Font.BOLD, 14));
        g2d.drawString(
                searchResult.name,
                WINDOW_PADDING * 3,
                (WINDOW_PADDING * 3) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2));
    }

    private void drawItemPrice(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("sans-serif", Font.BOLD, 14));
        g2d.drawString(
                searchResult.price + " coins",
                WINDOW_PADDING * 3,
                (WINDOW_PADDING * 4) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2));
    }

    private void drawItemImage(Graphics2D g2d) {
        Image itemImage = downloadItemImage();

        drawItemImageContainer(itemImage, g2d);

        g2d.drawImage(
                itemImage,
                Window.SIZE.width - (WINDOW_PADDING * 3) - itemImage.getWidth(this) + (ITEM_IMAGE_CONTAINER_PADDING / 2),
                (WINDOW_PADDING * 4) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2) - itemImage.getHeight(this),
                this);
    }

    private void drawItemImageContainer(Image itemImage, Graphics2D g2d) {
        g2d.setColor(new Color(1f, 1f, 1f, 0.9f));
        g2d.fillRoundRect(
                Window.SIZE.width - (WINDOW_PADDING * 3) - itemImage.getWidth(this),
                (WINDOW_PADDING * 4) + TEXT_BOX_HEIGHT + (TEXT_BOX_Y_PADDING * 2) - itemImage.getHeight(this) - (ITEM_IMAGE_CONTAINER_PADDING / 2),
                itemImage.getWidth(this) + ITEM_IMAGE_CONTAINER_PADDING,
                itemImage.getHeight(this) + ITEM_IMAGE_CONTAINER_PADDING,
                5,
                5);
    }

    private Image downloadItemImage() {
        try {
            return ImageIO.read(new URL(searchResult.imageUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}