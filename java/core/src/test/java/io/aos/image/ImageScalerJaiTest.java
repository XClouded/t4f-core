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
/*
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;

import org.junit.Test;

import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.SeekableStream;
*/
public class ImageScalerJaiTest {
/*
    @Test
    public static void test() {

        RenderedOp objImage = null;

        try {
            InputStream is = new FileInputStream("sun-soho011905-1919z.jpg");
            SeekableStream s = SeekableStream.wrapInputStream(is, true);
            objImage = JAI.create("stream", s);
            ((OpImage) objImage.getRendering()).setTileCache(null);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Width=" + objImage.getWidth());
        System.out.println("Height=" + objImage.getHeight());

        float xScale = 100.0F / objImage.getWidth();
        float yScale = 100.0F / objImage.getHeight();

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(objImage); // The source image
        pb.add(xScale); // The xScale
        pb.add(yScale); // The yScale
        pb.add(0.0F); // The x translation
        pb.add(0.0F); // The y translation
        pb.add(new InterpolationNearest()); // The interpolation

        System.out.println("xScale=" + xScale);
        System.out.println("yScale=" + yScale);

        objImage = JAI.create("scale", pb, null);
        
        try {
            File temp = new File("sun.jpg");
            JPEGEncodeParam encodeParam = new JPEGEncodeParam();
            encodeParam.setQuality(1F);
            encodeParam.setHorizontalSubsampling(0, 1);
            encodeParam.setHorizontalSubsampling(1, 1);
            encodeParam.setHorizontalSubsampling(2, 1);
            encodeParam.setVerticalSubsampling(0, 1);
            encodeParam.setVerticalSubsampling(1, 1);
            encodeParam.setVerticalSubsampling(2, 1);
            JAI.create("filestore", objImage, temp.getAbsolutePath(), "jpeg", encodeParam);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
*/
}
