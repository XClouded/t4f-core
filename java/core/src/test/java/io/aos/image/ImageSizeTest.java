package io.aos.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSizeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageSizeTest.class);
    
    @Test
    public void testReadImage() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("io/aos/image/sun.jpg"));
        LOGGER.info("Image Width=" + image.getWidth());
        LOGGER.info("Image Height=" + image.getHeight());
    }

}
