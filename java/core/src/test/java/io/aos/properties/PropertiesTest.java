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
package io.aos.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.junit.Test;

public class PropertiesTest {

    @Test
    public void test1() throws IOException {
        Properties prop1 = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("io/aos/properties/sample1.properties");
        prop1.load(is);
        System.out.println("Hello World Property=" + prop1.getProperty("hello.world"));
        prop1.setProperty("hello.world", "Hello World 2");
//        File outputFile = new File(ClassLoader.getSystemResource("target/out1.properties").getFile());
//        System.out.println(outputFile.getAbsolutePath());
//        prop1.store(new FileOutputStream(outputFile), null);
    }

    @Test
    public void test2() throws IOException {
        Properties prop2 = new Properties();
        prop2.load(new FileInputStream("./src/main/resources/io/aos/properties/sample1.properties"));
        System.out.println("Hello World Property=" + prop2.getProperty("hello.world"));
    }

    @Test
    public void test3() throws IOException {
        Properties prop3 = new Properties();
        Hashtable<String, String> hashtableProperties = new Hashtable<String, String>();
        prop3.load(new FileInputStream("./src/main/resources/io/aos/properties/sample1.properties"));
        Enumeration<Object> keyEnum = prop3.keys();
        while (keyEnum.hasMoreElements()) {
            String key = (String) keyEnum.nextElement();
            String value = prop3.getProperty(key);
            hashtableProperties.put(key, value);
            System.out.println(key + "=" + value);
        }
    }

    @Test
    public void test4() throws IOException {
        Properties props = new Properties();
        props.setProperty("recursiveSearch", "true");
        props.setProperty("noCopyPattern", "*.$$$");
        props.setProperty("maxLevel", "7");
        props.setProperty("fileName", "/home/aos/work.html");
        OutputStream propOut = new FileOutputStream(new File("./target/out2.properties"));
        boolean recursiveSearch = true;
        String noCopyPattern = "df";
        int maxLevel = 3;
        props.setProperty("recursiveSearch", "" + recursiveSearch);
        props.setProperty("noCopyPattern", noCopyPattern);
        props.setProperty("maxLevel", "" + maxLevel);
        props.store(propOut, "MacroProcessorProperties");
    }

}
