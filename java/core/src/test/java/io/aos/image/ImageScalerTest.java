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

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageScalerTest {

    public static final MediaTracker tracker = new MediaTracker(
            new Component() {
            });

    public void test() throws java.io.IOException {

        String inputFileName = "test.jpg";
        String outputFileName = "test-100.jpg";
        int outputWidth = Integer.parseInt("1");
        float outputQuality = Float.parseFloat("1");

        // Get input image
        Image inputImage = Toolkit.getDefaultToolkit().getImage(inputFileName);
        checkImage(inputImage);
        // System.out.println( "Input image size=" + inputImage.getWidth( null ) + "x" + inputImage.getHeight( null ) );

        // Create output image.
        Image outputImage = ImageScalerTest.setSize(inputImage, outputWidth, -1);
        checkImage(outputImage);
        // System.out.println( "Output image size=" + outputImage.getWidth( null ) + "x" + outputImage.getHeight( null ) );

        // Encode JPEG file.
        FileOutputStream fos = new FileOutputStream(outputFileName);
        encodeJPEG(fos, outputImage, outputQuality);
        fos.close();
        // For some reason, the MediaTracker/ImageProducer likes to hang on.
        System.exit(0);
    } 

    
    /** Adjusts the size of the image to the given coordinates.
     * If width or height is -1, the image aspect ration is maintained.
     */
    public static Image setSize(Image image, int width, int height) {
        return setSize(image, width, height, java.awt.Image.SCALE_DEFAULT);
    }

    /** Adjusts the size of the image to the given coordinates.
     * If width or height is -1, the image aspect ration is maintained.
     * <p>
     * Hints are one of SCALE_DEFAULT, SCALE_FAST, SCALE_SMOOTH,
     * SCALE_REPLICATE, SCALE_AREA_AVERAGING as defined in java.awt.Image.
     */
    public static Image setSize(Image image, int width, int height, int hints) {
        return image.getScaledInstance(width, height, hints);
    }

    /** Checks the given image for valid width and height. */
    public static void checkImage(Image image) {
        waitForImage(image);
        int imageWidth = image.getWidth(null);
        if (imageWidth < 1)
            throw new IllegalArgumentException("image width " + imageWidth
                    + " is out of range");
        int imageHeight = image.getHeight(null);
        if (imageHeight < 1)
            throw new IllegalArgumentException("image height " + imageHeight
                    + " is out of range");
        // System.out.println( "Image size=" + imageWidth + "x" + imageHeight );
    } 

    /** Waits for given image to load. Use before querying image height/width/colors. */
    public static void waitForImage(Image image) {
        try {
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            // loadStatus = tracker.statusID( 0, false );
            tracker.removeImage(image, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Encodes the given image at the given quality to the output stream. */
    public static void encodeJPEG(OutputStream outputStream, Image outputImage,
            float outputQuality) throws java.io.IOException {
        int outputWidth = outputImage.getWidth(null);
        if (outputWidth < 1)
            throw new IllegalArgumentException("output image width "
                    + outputWidth + " is out of range");
        int outputHeight = outputImage.getHeight(null);
        if (outputHeight < 1)
            throw new IllegalArgumentException("output image height "
                    + outputHeight + " is out of range");

        // Get a buffered image from the image.
        BufferedImage bi = new BufferedImage(outputWidth, outputHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D biContext = bi.createGraphics();
        biContext.drawImage(outputImage, 0, 0, null);
        // Note that additional drawing such as watermarks or logos can be placed here.
/*
        Use ImageIO, it can read/write JPEG, PNG, GIF, and BMP out of the box.
          ImageIO.write(ChartImage, "jpeg", out);
        Usually the static write(...) and read(...) methods are enough, if you need to control compression or handle image meta data, check out the Java Image I/O API Guide.
        http://docs.oracle.com/javase/7/docs/technotes/guides/imageio/spec/imageio_guideTOC.fm.html
*/        
        // com.sun.image.codec.jpeg package is included in sun and ibm sdk 1.3
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
        // The default quality is 0.75.
//        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bi);
//        jep.setQuality(outputQuality, true);
//        encoder.encode(bi, jep);
        // encoder.encode( bi );
        outputStream.flush();
    }

}
