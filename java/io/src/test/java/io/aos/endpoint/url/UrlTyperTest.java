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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTyperTest {

    public static void main(String... args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java URLTyper url");
            return;
        }

        InputStream inputStream = null;
    
        try {
            URL u = new URL(args[0]);
            inputStream = u.openStream();
            for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                System.out.write(c);
            }
            inputStream.close();
        }
        catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a URL Java understands.");
        }
        finally {
            if (inputStream != null)
                inputStream.close();
        }
    
    }
    
}
