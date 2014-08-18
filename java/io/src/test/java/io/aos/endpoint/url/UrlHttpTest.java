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
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UrlHttpTest {

    @Test
    public void testGet() throws MalformedURLException, URISyntaxException {

        URL url = new URL("http://www.google.com");
        url = new URL(url, "/images");
        System.out.println(url);
        System.out.println("protocol = " + url.getProtocol());
        System.out.println("authority = " + url.getAuthority());
        System.out.println("host = " + url.getHost());
        System.out.println("port = " + url.getPort());
        System.out.println("path = " + url.getPath());
        System.out.println("query = " + url.getQuery());
        System.out.println("filename = " + url.getFile());
        System.out.println("ref = " + url.getRef());

        URI uri = new URI("http://www.google.com/images");
        System.out.println(uri.toURL().toString());

    }

    @Test
    public void testPost1() throws IOException {

        URL url = new URL("http://www.yahoo.com");
        String content = "test content";

        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("content-type", "text/plain");
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestProperty("Content-Length", "" + content.length());

        OutputStream outputStream = httpUrlConnection.getOutputStream();
        outputStream.write(content.getBytes(), 0, content.length());
        outputStream.close();

        httpUrlConnection.connect();

        DataInputStream inputStream = new DataInputStream(httpUrlConnection.getInputStream());

        try {
            if (httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(httpUrlConnection.getResponseMessage());
            }
            else {
                while (true) {
                    System.out.print((char) inputStream.readUnsignedByte());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            httpUrlConnection.disconnect();
        }

        System.out.println("Response Code=" + httpUrlConnection.getResponseCode());
        System.out.println("Content = " + (String) httpUrlConnection.getContent());

    }

    @Test
    @Ignore
    public void testPost2() throws IOException {
        
        File file = new File("./src/test/resources/aos/link/station/url/aos.jpg");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[1024];
        int bytesRead; 
        while ((bytesRead = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        fis.close();
        
        String fileString = baos.toString("UTF-8");
        String urlParameters = "photo=" + URLEncoder.encode(fileString, "UTF-8");
    
        URL url = new URL("http://localhost:8000");
    
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpUrlConnection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes("UTF-8").length));
        httpUrlConnection.setRequestProperty("Content-Language", "en-US");
    
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
    
        DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
        dos.writeBytes(urlParameters);
        dos.flush();
        dos.close();
    
        InputStream is = httpUrlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = br.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        br.close();
        
        System.out.println(response.toString());
    
    }

    @Test
    public void testProxy() throws Exception {

        Properties systemSettings = System.getProperties();
        systemSettings.put("proxySet", "true");
        systemSettings.put("proxyHost", "10.255.170.65");
        systemSettings.put("proxyPort", "8080");
        System.setProperties(systemSettings);

        URL url = new URL("http://www.google.com/");
        URLConnection urlConnection = url.openConnection();
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String encoded = encoder.encode("eric.charles:saizwueh".getBytes());
        urlConnection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);

        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            System.out.println(inputLine);
        }
        br.close();

    }

}
