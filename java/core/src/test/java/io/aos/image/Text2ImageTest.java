/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * @see http://www.exampledepot.com/egs/java.awt.image/CreateBuf.html?l=rel
 * @see http://www.exampledepot.com/egs/java.awt.image/DrawOnImage.html?l=rel
 */
public class Text2ImageTest {

    /**
     * Use ImageIO, it can read/write JPEG, PNG, GIF, and BMP out of the box.
     * 
     * ImageIO.write(ChartImage, "jpeg", out);
     * 
     * Usually the static write(...) and read(...) methods are enough, 
     * if you need to control compression or handle image meta data, 
     * check out the Java Image I/O API Guide.
     * 
     * @see http://docs.oracle.com/javase/7/docs/technotes/guides/imageio/spec/imageio_guideTOC.fm.html
     */
    @Test
    public void testCreateImage() throws IOException {
        
        int width = 100;
        int height = 100;
        
        BufferedImage bimage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d1 = bimage1.createGraphics();
        // Draw on the image
        g2d1.setColor(Color.red);
        g2d1.fill(new Ellipse2D.Float(0, 0, 200, 100));
        g2d1.dispose();
        ImageIO.write(bimage1, "png", new File("./target/generatedimage1.png"));
        
        BufferedImage bimage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d2 = bimage2.createGraphics();
        g2d2.setColor(Color.yellow);
        g2d2.fill(new Rectangle2D.Float(20, 20, 100, 20));
//        g2d2.fill(new CubicCurve2D.Float(10, 20, 20, 90, 140, 100, 150, 130));
        g2d2.dispose();
        ImageIO.write(bimage2, "png", new File("./target/generatedimage2.png"));
        
        BufferedImage bimage3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d3 = bimage3.createGraphics();
        g2d3.setColor(Color.BLACK);
        g2d3.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        g2d3.drawString("tes&&\"ét", 0, 50);
        g2d3.dispose();
        ImageIO.write(bimage3, "png", new File("./target/generatedimage3.png"));
        
        int w = 200;
        int h = 200;
        BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bimage.createGraphics();
        g2.drawImage(bimage1, 0, 0, null);
        g2.drawImage(bimage2, 100, 0, null);
        g2.drawImage(bimage3, 0, 100, null);
        g2.dispose();
        ImageIO.write(bimage, "png", new File("./target/generatedimage.png"));
        
        BufferedImage b1 = ImageIO.read(new File("./src/test/resources/eric_normal.png"));
        BufferedImage b2 = ImageIO.read(new File("./src/test/resources/bookmark.png"));
        BufferedImage b3 = new BufferedImage(96, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = b3.createGraphics();
        g2d.drawImage(b1, 0, 0, null);
        g2d.drawImage(b2, 48, 0, null);
        g2d.dispose();
        ImageIO.write(b3, "png", new File("./target/ech-bookmark.png"));
/*        
        BufferedImage b1url = ImageIO.read(new File("./src/test/resources/bookmark.png"));
        BufferedImage b2url = ImageIO.read(new URL("http://s3.amazonaws.com/twitter_production/profile_images/115844442/eric_normal.png"));
//        BufferedImage b3url = new BufferedImage(96, 65, BufferedImage.TYPE_INT_ARGB);
        BufferedImage b3url = ImageIO.read(new File("./src/test/resources/container.png"));
        Graphics2D g2durl = b3url.createGraphics();
        g2durl.drawImage(b1url, 10, 7, null);
        g2durl.drawImage(b2url, 68, 7, null);
        g2durl.setColor(Color.BLACK);
        g2durl.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        g2durl.drawString("BlueBar, NewYork", 10, 72);
        g2durl.drawString("Favorite - 31/07/2009", 10, 84);
        g2durl.dispose();
        ImageIO.write(b3url, "png", new File("./target/ech-bookmark-url.png"));
*/        
    }
    
    public static void saveAsFile(BufferedImage img) throws IOException { 
//      FileOutputStream fos = new FileOutputStream("nameOfImageFileToSave.jpg"); 
//      JPEGImageEncoder encoder =  JPEGCodec.createJPEGEncoder(fos); 
//      encoder.encode(img); 
//      fos.flush(); 
//      fos.close();
    
    }

}
