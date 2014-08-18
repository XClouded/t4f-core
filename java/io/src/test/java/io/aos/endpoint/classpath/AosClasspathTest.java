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
package io.aos.endpoint.classpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import org.junit.Test;

/**
 * Show different methods to load resources from classpath.
 */
public class AosClasspathTest {
    public final String RESOURCE_CLASS_NAME = "test.txt";
    public final String RESOURCE_CANONICAL_CLASS_NAME = AosClasspathTest.class //
            .getPackage() //
            .getName() //
            .replaceAll("\\.", "/") //
            + "/" //
            + RESOURCE_CLASS_NAME //
            ;

    @Test
    public void loadResourceUrl1() throws ClassNotFoundException {
        URL url = Class.forName(Object.class.getCanonicalName()).getResource("/" + RESOURCE_CANONICAL_CLASS_NAME);
        print(url);
    }

    @Test
    public void loadResourceUrl2() throws ClassNotFoundException {
        URL url = Class.forName(AosClasspathTest.class.getCanonicalName()).getResource(RESOURCE_CLASS_NAME);
        print(url);
    }

    @Test
    public void loadResourceUrl3() throws ClassNotFoundException {
        URL url = ClassLoader.getSystemClassLoader().getResource(RESOURCE_CANONICAL_CLASS_NAME);
        print(url);
    }

    @Test
    public void loadResourceUrl4() {
        URL url = ClassLoader.getSystemResource(RESOURCE_CANONICAL_CLASS_NAME);
        print(url);
    }

    @Test
    public void loadResourceStream1() throws ClassNotFoundException {
        InputStream is = ClassLoader.getSystemResourceAsStream(RESOURCE_CANONICAL_CLASS_NAME);
        print(is);
    }

    @Test
    public void loadResourceStream2() throws ClassNotFoundException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(RESOURCE_CANONICAL_CLASS_NAME);
        print(is);
    }

    @Test
    public void loadResourceStream3() throws ClassNotFoundException {
        System.out.println(RESOURCE_CANONICAL_CLASS_NAME);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(RESOURCE_CANONICAL_CLASS_NAME);
        print(is);
    }

    private void print(URL url) {
        System.out.println(url.getPath());
        File f = new File(url.getPath());
        System.out.println(f.getAbsolutePath());
    }

    private void print(InputStream is) {
        // printBytes(is);
        printChar(is);
    }

    private void printBytes(InputStream is) {
        byte[] b = new byte[20];
        int l = 0;
        try {
            while ((l = is.read(b)) != -1) {
                if (l != b.length) {
                    System.out.print(new String(Arrays.copyOf(b, l), "UTF-8"));
                }
                else {
                    System.out.print(new String(b, "UTF-8"));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    private void printChar(InputStream is) {
        char[] c = new char[20];
        InputStreamReader isr = new InputStreamReader(is);
        int l = 0;
        try {
            while ((l = isr.read(c)) != -1) {
                if (l != c.length) {
                    System.out.print(new String(c));
                }
                else {
                    System.out.print(new String(c));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

}
