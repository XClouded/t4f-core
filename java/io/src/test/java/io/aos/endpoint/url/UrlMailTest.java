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
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Ignore;
import org.junit.Test;

public class UrlMailTest {

    @Test
    @Ignore
    public void test() throws IOException {

        OutputStream outputStream = null;

        try {
            URL u = new URL("mailto:eric@echarles.net");
            URLConnection uc = u.openConnection();
            uc.setDoOutput(true);
            uc.connect();
            outputStream = uc.getOutputStream();
            for (int c = System.in.read(); c != -1; c = System.in.read()) {
                outputStream.write(c);
            }
        } catch (IOException ex) {
            System.err.println(ex);
            throw ex;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }
}
