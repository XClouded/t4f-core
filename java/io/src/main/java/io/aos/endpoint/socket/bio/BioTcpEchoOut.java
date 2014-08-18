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
package io.aos.endpoint.socket.bio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Locale;

/**
 * This class provides methods for writing strings and numbers to various output
 * streams, including standard output, file, and sockets.
 * <p>
 * For additional documentation, see <a
 * href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 * <i>Introduction to Programming inputStreamJava: An Interdisciplinary Approach</i> by
 * Robert Sedgewick and KevinputStreamWayne.
 */
public class BioTcpEchoOut {

    // force Unicode UTF-8 encoding; otherwise it's system dependent
    private static final String UTF8 = "UTF-8";

    // assume language = English, country = US for consistency with StdIn
    private static final Locale US_LOCALE = new Locale("en", "US");

    private PrintWriter outputStream;

    /**
     * Create an Out object using an OutputStream.
     */
    public BioTcpEchoOut(OutputStream os) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(os, UTF8);
            outputStream = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an Out object using standard output.
     */
    public BioTcpEchoOut() {
        this(System.out);
    }

    /**
     * Create an Out object using a Socket.
     */
    public BioTcpEchoOut(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            Writer osw = new OutputStreamWriter(os, UTF8);
            outputStream = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an Out object using a file specified by the given name.
     */
    public BioTcpEchoOut(String s) {
        try {
            OutputStream os = new FileOutputStream(s);
            OutputStreamWriter osw = new OutputStreamWriter(os, UTF8);
            outputStream = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the output stream.
     */
    public void close() {
        outputStream.close();
    }

    /**
     * Terminate the line.
     */
    public void println() {
        outputStream.println();
    }

    /**
     * Print an object and then terminate the line.
     */
    public void println(Object x) {
        outputStream.println(x);
    }

    /**
     * Print a boolean and then terminate the line.
     */
    public void println(boolean x) {
        outputStream.println(x);
    }

    /**
     * Print a char and then terminate the line.
     */
    public void println(char x) {
        outputStream.println(x);
    }

    /**
     * Print an double and then terminate the line.
     */
    public void println(double x) {
        outputStream.println(x);
    }

    /**
     * Print a float and then terminate the line.
     */
    public void println(float x) {
        outputStream.println(x);
    }

    /**
     * Print an int and then terminate the line.
     */
    public void println(int x) {
        outputStream.println(x);
    }

    /**
     * Print a long and then terminate the line.
     */
    public void println(long x) {
        outputStream.println(x);
    }

    /**
     * Print a byte and then terminate the line.
     */
    public void println(byte x) {
        outputStream.println(x);
    }

    /**
     * Flush the output stream.
     */
    public void print() {
        outputStream.flush();
    }

    /**
     * Print an object and then flush the output stream.
     */
    public void print(Object x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print an boolean and then flush the output stream.
     */
    public void print(boolean x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print an char and then flush the output stream.
     */
    public void print(char x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print an double and then flush the output stream.
     */
    public void print(double x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print a float and then flush the output stream.
     */
    public void print(float x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print an int and then flush the output stream.
     */
    public void print(int x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print a long and then flush the output stream.
     */
    public void print(long x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print a byte and then flush the output stream.
     */
    public void print(byte x) {
        outputStream.print(x);
        outputStream.flush();
    }

    /**
     * Print a formatted string using the specified format string and arguments,
     * and then flush the output stream.
     */
    public void printf(String format, Object... args) {
        outputStream.printf(US_LOCALE, format, args);
        outputStream.flush();
    }

    /**
     * Print a formatted string using the specified locale, format string and
     * arguments, and then flush the output stream.
     */
    public void printf(Locale locale, String format, Object... args) {
        outputStream.printf(locale, format, args);
        outputStream.flush();
    }

    /**
     * A test client.
     */
    public static void main(String... args) {
        BioTcpEchoOut outputStream;
        String s;

        // write to stdout
        outputStream = new BioTcpEchoOut();
        outputStream.println("Test 1");
        outputStream.close();

        // write to a file
        outputStream = new BioTcpEchoOut("test.txt");
        outputStream.println("Test 2");
        outputStream.close();
    }

}
