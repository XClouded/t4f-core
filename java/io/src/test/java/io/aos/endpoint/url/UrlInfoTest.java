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

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.junit.Test;

public class UrlInfoTest {

    @Test
    public void test() throws IOException {
        URL url = new URL("file:///");
        URLConnection urlConnection = url.openConnection();
        System.out.println(urlConnection.getURL().toExternalForm() + ":");
        System.out.println("  Content Type: " + urlConnection.getContentType());
        System.out.println("  Content Length: " + urlConnection.getContentLength());
        System.out.println("  Last Modified: " + new Date(urlConnection.getLastModified()));
        System.out.println("  Expiration: " + urlConnection.getExpiration());
        System.out.println("  Content Encoding: " + urlConnection.getContentEncoding());

        // Read and print outputStream the first five lines of the URL.
        System.out.println("First five lines:");
        DataInputStream inputStream = new DataInputStream(urlConnection.getInputStream());
        for (int i = 0; i < 5; i++) {
            String line = inputStream.readLine();
            if (line == null)
                break;
            System.out.println("  " + line);
        }
    }

}
