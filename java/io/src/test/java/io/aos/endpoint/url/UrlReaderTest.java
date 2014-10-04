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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class UrlReaderTest {

    @Test
    public void test1() throws Exception {
        URL url = new URL("http://www.google.com/");
        BufferedReader inputStream= new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = inputStream.readLine()) != null) {
             System.out.println(inputLine);
        }
        inputStream.close();
    }

    @Test
    public void test2() throws Exception {
        URL url = new URL("http://www.google.com");
        URLConnection urlConnection = url.openConnection();
        BufferedReader inputStream= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();
        while ((inputLine = inputStream.readLine()) != null) {
            System.out.println(inputLine);
            stringBuffer.append(inputLine);
        }
        inputStream.close();
        System.out.println(stringBuffer);
    }

    @Test
    public void test3() throws Exception {
        URL url = new URL("http://www.google.com");
        BufferedReader inputStream= new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = inputStream.readLine()) != null) {
            System.out.println(inputLine);
            sb.append(inputLine);
        }
        inputStream.close();
        System.out.println(new String(sb));
    }

    @Test
    public void testGetUrl() throws Exception {
        setProxy("", 0, "", "");
        URL yahoo = new URL("http://www.yahoo.com/");
        BufferedReader inputStream= new BufferedReader(new InputStreamReader(yahoo.openStream()));
        String inputLine;
        while ((inputLine = inputStream.readLine()) != null) {
            System.out.println(inputLine);
        }
        inputStream.close();
    }

    private void setProxy(String httpProxyHost, Integer httpProxyPort, String httpProxyUsername, String httpProxyPassword) {
        System.setProperty("http.proxyHost", httpProxyHost);
        System.setProperty("http.proxyPort", httpProxyPort.toString());
        System.setProperty("http.proxyUse", httpProxyUsername);
        System.setProperty("http.proxyPassword", httpProxyPassword);
    }

    private void retrieveInformation() throws IOException {
        File file = new File("url.html");
        FileOutputStream fos = new FileOutputStream(file);
        String urlString = new String("http://www.umg.be/");
        readHtmlPageAndWriteFile(urlString, fos);
        urlString = new String("http://www.umg.be/aos/_resources/skin/u-mangate.com/WEB/images/umg.be/_u-mangate.logoH-250-47.png");
        file = new File("umg.png");
        fos = new FileOutputStream(file);
        readImageAndWriteFile(urlString, fos);
        for (int i = 69; i < 100; i++) {
            urlString = new String("http://www.gigaweb.be/free-templates/0" + i + ".zip");
            file = new File("./downloaded-skins/0" + i + ".zip");
            fos = new FileOutputStream(file);
            readObjectAndWriteFile(urlString, fos);
        }

    }

    private void readHtmlPageAndWriteFile(String urlString, FileOutputStream fos) throws IOException {
        URL url = new URL(urlString);
        BufferedReader inputStream= new BufferedReader(new InputStreamReader(url.openStream()));
        int c;
        while ((c = inputStream.read()) != -1) {
            fos.write(c);
        }
        fos.close();
        inputStream.close();
    }

    private void readImageAndWriteFile(String urlString, FileOutputStream fos) throws IOException {
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        int c;
        while ((c = inputStream.read()) != -1) {
            fos.write(c);
        }
        fos.close();
        inputStream.close();
    }

    private static void readObjectAndWriteFile(String urlString, FileOutputStream fos) throws IOException {
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        int c;
        while ((c = inputStream.read()) != -1) {
            fos.write(c);
        }
        fos.close();
        inputStream.close();
    }

}
