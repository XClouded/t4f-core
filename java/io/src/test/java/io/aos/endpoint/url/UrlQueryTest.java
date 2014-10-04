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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.junit.Test;

public class UrlQueryTest {

    @Test
    public void test1() {
        QueryString qs = new UrlQueryTest().new QueryString("pg", "q");
        qs.add("kl", "XX");
        qs.add("stype", "stext");
        qs.add("q", "+\"Java Network Programming\"");
        String url = "http://www.altavista.com/cgi-bin/query?" + qs;
        System.out.println(url);
    }

    @Test
    public void test2() {

        String target = "test";

        QueryString query = new QueryString("search", target);
        try {
            URL u = new URL("http://search.dmoz.org/cgi-bin/search?" + query);
            InputStream inputStream = new BufferedInputStream(u.openStream());
            InputStreamReader theHTML = new InputStreamReader(inputStream);
            int c;
            while ((c = theHTML.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public class QueryString {

        private String query;

        public QueryString(Object name, Object value) {
            try {
                query = URLEncoder.encode(name.toString(), Charset.defaultCharset().displayName()) + "="
                        + URLEncoder.encode(value.toString(), Charset.defaultCharset().displayName());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("Unsupported Encoding Exception", e);
            }
        }

        public QueryString() {
            query = "";
        }

        public synchronized void add(Object name, Object value) {

            if (!query.trim().equals("")) {
                query += "&";
            }
            try {
                query += URLEncoder.encode(name.toString(), Charset.defaultCharset().displayName()) + "="
                        + URLEncoder.encode(value.toString(), Charset.defaultCharset().displayName());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("Unsupported Encoding Exception", e);
            }

        }

        public String toString() {
            return query;
        }

    }

}
