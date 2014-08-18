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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageScalerGraphics2DTest {

    @Test
    public void test() throws IOException {
        
        String srcPath = Thread.currentThread().getContextClassLoader().getResource("io/aos/image/sun.jpg").toString();
        String dstPath = "./target/sun1.jpg";
        
        File srcFile = new File(srcPath.replace("file:", ""));
        File dstFile = new File(dstPath);
        
        int dstWidth = 500;

        BufferedImage bsrc = ImageIO.read(srcFile);
    
        int dstHeight = dstWidth*bsrc.getHeight()/bsrc.getWidth();
    
        BufferedImage bdest = new BufferedImage(dstWidth, dstHeight,
                BufferedImage.TYPE_INT_RGB);
    
        Graphics2D g = bdest.createGraphics();
    
        AffineTransform at = AffineTransform.getScaleInstance(
                (double) dstWidth / bsrc.getWidth(), (double) dstHeight
                        / bsrc.getHeight());
    
        g.drawRenderedImage(bsrc, at);
    
        ImageIO.write(bdest, "JPG", dstFile);
    
    }

}
