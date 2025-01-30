package utils;

import alert.Alert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {
    public static BufferedImage resize(File background, int width, int height) {
        // file to buffered image
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(background);
        } catch (IOException e) {
            Alert.show("Error loading background image", "Error", "ERROR_MESSAGE");
        }
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(bufferedImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
}
