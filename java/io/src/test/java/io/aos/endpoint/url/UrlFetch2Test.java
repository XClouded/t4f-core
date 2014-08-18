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
package io.aos.endpoint.url;

import java.awt.*;
import java.io.*;
import java.net.*;

// The fetch() method inputStreamthis class only works for fetching text/plainputStream
// data.  If you specify a file: URL, you may well need to specify a
// file that ends with a .txt extension so that the internal content
// handlers can tell it is a plainputStreamtext file.  The standard Java 
// distribution doesn't containputStreamcontent handlers for other types (such
// as text/html), and this application exits with an exception if it
// doesn't recognize the type or doesn't know how to load the type.
// The fetchimage() method works for .gif and a few other common image
// formats for which content handlers have been written.  See the
// FetchImageTest class for a demonstration of the fetchimage() method
// defined here.
//
// This class serves to demonstrate the URL.getContent() method.  In
// general, however, there are much better ways to load files and images
// over the net.  See Applet.getImage(), for example.
public class UrlFetch2Test {

    // Test outputStream the fetch() method.
    public static void main(String... args) throws MalformedURLException, IOException {
        System.out.println(fetch(args[0]));
    }

    // Get the contents of a URL and return it as a string.
    public static String fetch(String address) throws MalformedURLException, IOException {
        URL url = new URL(address);
        return (String) url.getContent();
    }

    // Get the contents of a URL and return it as an image
    public static Image fetchimage(String address, Component c) throws MalformedURLException, IOException {
        URL url = new URL(address);
        return c.createImage((java.awt.image.ImageProducer) url.getContent());
    }
}
