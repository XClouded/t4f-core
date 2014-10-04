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

import java.net.URLEncoder;

import org.junit.Test;

public class UrlEncoderTest {

    @Test
    public void test1() {

        String input = "http://www.altavista.com/cgi-bin/query?pg=q&kl=XX&stype=stext&q=+\"Java Network Programming\"";
        System.out.println(input);
        String output = URLEncoder.encode(input);
        System.out.println(output);

        String s = URLEncoder.encode("http");
        s += "://";
        s += URLEncoder.encode("www.altavista.com");
        s += "/";
        s += URLEncoder.encode("cgi-bin");
        s += "/";
        s += URLEncoder.encode("query");
        s += "?";
        s += URLEncoder.encode("pg");
        s += "=";
        s += URLEncoder.encode("q");
        s += "&";
        s += URLEncoder.encode("kl");
        s += "=";
        s += URLEncoder.encode("XX");
        s += "&";
        s += URLEncoder.encode("stype");
        s += "=";
        s += URLEncoder.encode("stext");
        s += "&";
        s += URLEncoder.encode("q");
        s += "=";
        s += URLEncoder.encode("\"Java Network Programming\"");
        System.out.println(s);

    }

    @Test
    public void test2() {

        System.out.println(URLEncoder.encode("This string has spaces"));
        System.out.println(URLEncoder.encode("This*string*has*asterisks"));
        System.out.println(URLEncoder.encode("This%string%has%percent%signs"));
        System.out.println(URLEncoder.encode("This+string+has+pluses"));
        System.out.println(URLEncoder.encode("This/string/has/slashes"));
        System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks"));
        System.out.println(URLEncoder.encode("This:string:has:colons"));
        System.out.println(URLEncoder.encode("This~string~has~tildes"));
        System.out.println(URLEncoder.encode("This(string)has(parentheses)"));
        System.out.println(URLEncoder.encode("This.string.has.periods"));
        System.out.println(URLEncoder.encode("This=string=has=equals=signs"));
        System.out.println(URLEncoder.encode("This&string&has&ampersands"));

    }

}
