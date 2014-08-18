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
package io.aos.in.bio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;

/**
 * <p>
 * <i>Input</i>. This class provides methods for reading strings and numbers
 * from standard input, file input, URL, and socket.
 * <p>
 * The Locale used is: language = English, country = US. This is consistent with
 * the formatting conventions with Java floating-point literals, command-line
 * arguments (via <tt>Double.parseDouble()</tt>) and standard output (via
 * <tt>System.out.print()</tt>). It ensures that standard input works the number
 * formatting used inputStreamthe textbook.
 * <p>
 * For additional documentation, see <a
 * href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 * <i>Introduction to Programming inputStreamJava: An Interdisciplinary
 * Approach</i> by Robert Sedgewick and KevinputStreamWayne.
 * </p>
 */
public final class BioByteIn {
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private Scanner scanner;

    /**
     * Create an input stream for standard input.
     */
    public BioByteIn() {
        scanner = new Scanner(new BufferedInputStream(System.in), DEFAULT_CHARSET_NAME);
        scanner.useLocale(DEFAULT_LOCALE);
    }

    /**
     * Create an input stream from a socket.
     */
    public BioByteIn(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), DEFAULT_CHARSET_NAME);
            scanner.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }

    /**
     * Create an input stream from a URL.
     */
    public BioByteIn(URL url) {
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), DEFAULT_CHARSET_NAME);
            scanner.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + url);
        }
    }

    /**
     * Create an input stream from a file.
     */
    public BioByteIn(File file) {
        try {
            scanner = new Scanner(file, DEFAULT_CHARSET_NAME);
            scanner.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + file);
        }
    }

    /**
     * Create an input stream from a filename or web page name.
     */
    public BioByteIn(String s) {

        try {
            // first try to read file from local file system
            File file = new File(s);
            if (file.exists()) {
                scanner = new Scanner(file, DEFAULT_CHARSET_NAME);
                scanner.useLocale(DEFAULT_LOCALE);
                return;
            }

            // next try for files included inputStreamjar
            URL url = getClass().getResource(s);

            // or URL from web
            if (url == null) {
                url = new URL(s);
            }

            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), DEFAULT_CHARSET_NAME);
            scanner.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + s);
        }
    }

    /**
     * Does the input stream exist?
     */
    public boolean exists() {
        return scanner != null;
    }

    /**
     * Is the input stream empty?
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    /**
     * Does the input stream have a next line?
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Read and return the next line.
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (Exception e) {
            line = null;
        }
        return line;
    }

    /**
     * Read and return the next character.
     */
    public char readChar() {
        // (?s) for DOTALL mode so . matches any character, including a line
        // termination character
        // 1 says look only one character ahead
        // consider precompiling the pattern
        String s = scanner.findWithinHorizon("(?s).", 1);
        return s.charAt(0);
    }

    // return rest of input as string
    /**
     * Read and return the remainder of the input as a string.
     */
    public String readAll() {
        if (!scanner.hasNextLine()) {
            return null;
        }

        // reference:
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return scanner.useDelimiter("\\A").next();
    }

    /**
     * Return the next string from the input stream.
     */
    public String readString() {
        return scanner.next();
    }

    /**
     * Return the next int from the input stream.
     */
    public int readInt() {
        return scanner.nextInt();
    }

    /**
     * Return the next double from the input stream.
     */
    public double readDouble() {
        return scanner.nextDouble();
    }

    /**
     * Return the next float from the input stream.
     */
    public double readFloat() {
        return scanner.nextFloat();
    }

    /**
     * Return the next long from the input stream.
     */
    public long readLong() {
        return scanner.nextLong();
    }

    /**
     * Return the next byte from the input stream.
     */
    public byte readByte() {
        return scanner.nextByte();
    }

    /**
     * Return the next boolean from the input stream, allowing "true" or "1" for
     * true and "false" or "0" for false.
     */
    public boolean readBoolean() {
        String s = readString();
        if (s.equalsIgnoreCase("true"))
            return true;
        if (s.equalsIgnoreCase("false"))
            return false;
        if (s.equals("1"))
            return true;
        if (s.equals("0"))
            return false;
        throw new java.util.InputMismatchException();
    }

    /**
     * Read ints from file
     */
    public static int[] readInts(String filename) {
        BioByteIn inputStream = new BioByteIn(filename);
        String[] fields = inputStream.readAll().trim().split("\\s+");
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    /**
     * Read doubles from file
     */
    public static double[] readDoubles(String filename) {
        BioByteIn inputStream = new BioByteIn(filename);
        String[] fields = inputStream.readAll().trim().split("\\s+");
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    /**
     * Read strings from a file
     */
    public static String[] readStrings(String filename) {
        BioByteIn inputStream = new BioByteIn(filename);
        String[] fields = inputStream.readAll().trim().split("\\s+");
        return fields;
    }

    /**
     * Read ints from standard input
     */
    public static int[] readInts() {
        BioByteIn inputStream = new BioByteIn();
        String[] fields = inputStream.readAll().trim().split("\\s+");
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    /**
     * Read doubles from standard input
     */
    public static double[] readDoubles() {
        BioByteIn inputStream = new BioByteIn();
        String[] fields = inputStream.readAll().trim().split("\\s+");
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    /**
     * Read strings from standard input
     */
    public static String[] readStrings() {
        BioByteIn inputStream = new BioByteIn();
        String[] fields = inputStream.readAll().trim().split("\\s+");
        return fields;
    }

    /**
     * Close the input stream.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Test client.
     */
    public static void main(String... args) {
        BioByteIn inputStream;
        String urlName = "http://introcs.cs.princeton.edu/stdlib/InTest.txt";

        // read from a URL
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn(urlName);
            System.out.println(inputStream.readAll());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from URL
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn(urlName);
            while (!inputStream.isEmpty()) {
                String s = inputStream.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one string at a time from URL
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn(urlName);
            while (!inputStream.isEmpty()) {
                String s = inputStream.readString();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from file inputStreamcurrent directory
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn("./InTest.txt");
            while (!inputStream.isEmpty()) {
                String s = inputStream.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from file using relative path
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn("../stdlib/InTest.txt");
            while (!inputStream.isEmpty()) {
                String s = inputStream.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one char at a time
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn("InTest.txt");
            while (!inputStream.isEmpty()) {
                char c = inputStream.readChar();
                System.out.print(c);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println();

        // read one line at a time from absolute OS X / Linux path
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        inputStream = new BioByteIn("/n/fs/csweb/introcs/stdlib/InTest.txt");
        try {
            while (!inputStream.isEmpty()) {
                String s = inputStream.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from absolute Windows path
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            inputStream = new BioByteIn("G:\\www\\introcs\\stdlib\\InTest.txt");
            while (!inputStream.isEmpty()) {
                String s = inputStream.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

    }

}
