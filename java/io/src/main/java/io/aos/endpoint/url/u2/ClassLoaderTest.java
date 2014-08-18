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

import java.net.URL;
import java.net.URLConnection;

public class ClassLoaderTest {

    public static void main(String... args) {

        try {
            System.out.println("With the URLConnection's class loader");
            URL u = new URL("http://metalab.unc.edu/javafaq/");
            URLConnection uc = u.openConnection();
            Object o = Class.forName("com.macfaq.net.www.content.text.tab", true, u.getClass().getClassLoader());
            System.out.println(o);
            System.out.println(o.getClass());
            System.out.println(o.getClass().getClassLoader());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("With a null class loader");
            Object o = Class.forName("com.macfaq.net.www.content.text.tab", true, null);
            System.out.println(o);
            System.out.println(o.getClass());
            System.out.println(o.getClass().getClassLoader());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("With the single argument forName method");
            Object o = Class.forName("com.macfaq.net.www.content.text.tab");
            System.out.println(o);
            System.out.println(o.getClass());
            System.out.println(o.getClass().getClassLoader());
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

}
