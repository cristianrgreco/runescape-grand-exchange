package rge.util;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {
    private ResourceLoader() {
    }

    public static Image image(String filename, Class<?> contextClass) {
        try {
            URL imageResource = contextClass.getClassLoader().getResource(filename);
            if (imageResource == null) {
                throw new FileNotFoundException("Resource '" + filename + "' not found!");
            }
            return ImageIO.read(new File(imageResource.getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font font(String filename, Class<?> contextClass) {
        try {
            Font fontResource = Font.createFont(Font.TRUETYPE_FONT, contextClass.getClassLoader().getResourceAsStream(filename));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontResource);
            return fontResource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
