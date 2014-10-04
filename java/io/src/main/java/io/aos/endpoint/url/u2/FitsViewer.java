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
package io.aos.endpoint.url.u2;

import java.awt.*;

import javax.swing.*;

import java.awt.image.*;
import java.net.*;
import java.io.*;

public class FitsViewer extends JFrame {
    private static final long serialVersionUID = 1L;

    private URL url;
    private Image image;

    public static void main(String... args) {
    
        URLConnection.setContentHandlerFactory(new FitsFactory());
        for (int i = 0; i < args.length; i++) {
            try {
                FitsViewer f = new FitsViewer(new URL(args[i]));
                f.setSize(252, 252);
                f.loadImage();
                f.show();
            }
            catch (MalformedURLException e) {
                System.err.println(args[i] + " is not a URL I recognize.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    }

    public FitsViewer(URL u) {
        super(u.getFile());
        this.url = u;
    }

    public void loadImage() throws IOException {

        Object content = this.url.getContent();
        ImageProducer producer;
        try {
            producer = (ImageProducer) content;
        }
        catch (ClassCastException e) {
            throw new IOException("Unexpected type " + content.getClass());
        }
        if (producer == null)
            image = null;
        else {
            image = this.createImage(producer);
            int width = image.getWidth(this);
            int height = image.getHeight(this);
            // add space for title bar on top
            height += this.getInsets().top;
            if (width > 0 && height > 0)
                this.setSize(width, height);
        }

    }

    public void paint(Graphics g) {
        if (image != null)
            g.drawImage(image, 0, 0, this);
    }

}
