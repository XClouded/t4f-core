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


import io.aos.endpoint.url.UrlQueryTest.QueryString;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlFormTest {

    private URL url;
    // from Chapter 7, Example 7-9
    private QueryString query = new UrlQueryTest().new QueryString();

    public UrlFormTest(URL url) throws IllegalArgumentException {
        if (!url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Posting only works for http URLs");
        }
        this.url = url;
    }

    public void add(String name, String value) {
        query.add(name, value);
    }

    public URL getURL() {
        return this.url;
    }

    public InputStream post() throws IOException {

        // open the connection and prepare it to POST
        URLConnection uc = url.openConnection();
        System.out.println("do output: " + uc.getDoOutput());
        System.out.println("do input: " + uc.getDoInput());
        uc.setDoOutput(true);
        System.out.println("do output: " + uc.getDoOutput());
        System.out.println("do input: " + uc.getDoInput());
        OutputStreamWriter outputStream = new OutputStreamWriter(new BufferedOutputStream(uc.getOutputStream()), "ASCII");
        InputStream inputStream = uc.getInputStream();

        // The POST line, the Content-type header,
        // and the Content-length headers are sent by the URLConnection.
        // We just need to send the data
        outputStream.write(query.toString());
        outputStream.write("\r\n");
        outputStream.flush();
        outputStream.close();

        // Return the response
        return inputStream;

    }

    public static void main(String... args) {

        URL url;

        if (args.length > 0) {
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                System.err.println("Usage: java FormPoster url");
                return;
            }
        } else {
            try {
                url = new URL("http://hoohoo.ncsa.uiuc.edu/cgi-bin/post-query");
            } catch (MalformedURLException e) { // shouldn't happen
                System.err.println(e);
                return;
            }
        }

        UrlFormTest poster = new UrlFormTest(url);
        poster.add("name", "Elliotte Rusty Harold");
        poster.add("email", "elharo@metalab.unc.edu");

        try {
            InputStream inputStream = poster.post();
            inputStream= new BufferedInputStream(inputStream);
            // Read the response
            InputStreamReader r = new InputStreamReader(inputStream);
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
            inputStream.close();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

}
