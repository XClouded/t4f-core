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

import java.awt.image.MemoryImageSource;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.Hashtable;

public class XFits extends ContentHandler {

    public Object getContent(URLConnection uc) throws IOException {

        int width = -1;
        int height = -1;
        int bitpix = 16;
        int[] data = null;
        int naxis = 2;
        Hashtable header = null;

        DataInputStream dis = new DataInputStream(uc.getInputStream());
        header = readHeader(dis);

        // should we return null or throw an IOException????
        bitpix = getIntFromHeader("BITPIX  ", -1, header);
        if (bitpix <= 0)
            return null;
        naxis = getIntFromHeader("NAXIS   ", -1, header);
        if (naxis < 1)
            return null;
        width = getIntFromHeader("NAXIS1  ", -1, header);
        if (width <= 0)
            return null;
        if (naxis == 1)
            height = 1;
        else
            height = getIntFromHeader("NAXIS2  ", -1, header);
        if (height <= 0)
            return null;

        if (bitpix == 16) {
            short[] theInput = new short[height * width];
            for (int i = 0; i < theInput.length; i++) {
                theInput[i] = dis.readShort();
            }
            data = scaleArray(theInput);
        }
        else if (bitpix == 32) {
            int[] theInput = new int[height * width];
            for (int i = 0; i < theInput.length; i++) {
                theInput[i] = dis.readInt();
            }
            data = scaleArray(theInput);
        }
        else if (bitpix == 64) {
            long[] theInput = new long[height * width];
            for (int i = 0; i < theInput.length; i++) {
                theInput[i] = dis.readLong();
            }
            data = scaleArray(theInput);
        }
        else if (bitpix == -32) {
            float[] theInput = new float[height * width];
            for (int i = 0; i < theInput.length; i++) {
                theInput[i] = dis.readFloat();
            }
            data = scaleArray(theInput);
        }
        else if (bitpix == -64) {
            double[] theInput = new double[height * width];
            for (int i = 0; i < theInput.length; i++) {
                theInput[i] = dis.readDouble();
            }
            data = scaleArray(theInput);
        }
        else {
            System.err.println("Invalid BITPIX");
            return null;
        } // end if-else-if

        return new MemoryImageSource(width, height, data, 0, width);

    } // end getContent

    private Hashtable readHeader(DataInputStream dis) throws IOException {

        int blocksize = 2880;
        int fieldsize = 80;
        String key, value;
        int linesRead = 0;

        byte[] buffer = new byte[fieldsize];

        Hashtable header = new Hashtable();
        while (true) {
            dis.readFully(buffer);
            key = new String(buffer, 0, 8, "ASCII");
            linesRead++;
            if (key.substring(0, 3).equals("END"))
                break;
            if (buffer[8] != '=' || buffer[9] != ' ')
                continue;
            value = new String(buffer, 10, 20, "ASCII");
            header.put(key, value);
        }
        int linesLeftToRead = (blocksize - ((linesRead * fieldsize) % blocksize)) / fieldsize;
        for (int i = 0; i < linesLeftToRead; i++)
            dis.readFully(buffer);

        return header;

    }

    private int getIntFromHeader(String name, int defaultValue, Hashtable header) {

        String s = "";
        int result = defaultValue;

        try {
            s = (String) header.get(name);
        }
        catch (NullPointerException e) {
            return defaultValue;
        }
        try {
            result = Integer.parseInt(s.trim());
        }
        catch (NumberFormatException e) {
            System.err.println(e);
            System.err.println(s);
            return defaultValue;
        }

        return result;

    }

    // parameterized types (templates) would help a lot here
    private int[] scaleArray(short[] theInput) {

        int data[] = new int[theInput.length];
        int max = 0;
        int min = 0;
        for (int i = 0; i < theInput.length; i++) {
            if (theInput[i] > max)
                max = theInput[i];
            if (theInput[i] < min)
                min = theInput[i];
        }
        long r = max - min;
        double a = 255.0 / r;
        double b = -a * min;
        int opaque = 255;
        for (int i = 0; i < data.length; i++) {
            int temp = (int) (theInput[i] * a + b);
            data[i] = (opaque << 24) | (temp << 16) | (temp << 8) | temp;
        }
        return data;

    }

    private int[] scaleArray(int[] theInput) {

        int data[] = new int[theInput.length];
        int max = 0;
        int min = 0;
        for (int i = 0; i < theInput.length; i++) {
            if (theInput[i] > max)
                max = theInput[i];
            if (theInput[i] < min)
                min = theInput[i];
        }
        long r = max - min;
        double a = 255.0 / r;
        double b = -a * min;
        int opaque = 255;
        for (int i = 0; i < data.length; i++) {
            int temp = (int) (theInput[i] * a + b);
            data[i] = (opaque << 24) | (temp << 16) | (temp << 8) | temp;
        }
        return data;

    }

    private int[] scaleArray(long[] theInput) {

        int data[] = new int[theInput.length];
        long max = 0;
        long min = 0;
        for (int i = 0; i < theInput.length; i++) {
            if (theInput[i] > max)
                max = theInput[i];
            if (theInput[i] < min)
                min = theInput[i];
        }
        long r = max - min;
        double a = 255.0 / r;
        double b = -a * min;
        int opaque = 255;
        for (int i = 0; i < data.length; i++) {
            int temp = (int) (theInput[i] * a + b);
            data[i] = (opaque << 24) | (temp << 16) | (temp << 8) | temp;
        }
        return data;

    }

    private int[] scaleArray(double[] theInput) {

        int data[] = new int[theInput.length];
        double max = 0;
        double min = 0;
        for (int i = 0; i < theInput.length; i++) {
            if (theInput[i] > max)
                max = theInput[i];
            if (theInput[i] < min)
                min = theInput[i];
        }
        double r = max - min;
        double a = 255.0 / r;
        double b = -a * min;
        int opaque = 255;
        for (int i = 0; i < data.length; i++) {
            int temp = (int) (theInput[i] * a + b);
            data[i] = (opaque << 24) | (temp << 16) | (temp << 8) | temp;
        }
        return data;

    }

    private int[] scaleArray(float[] theInput) {

        int data[] = new int[theInput.length];
        float max = 0;
        float min = 0;
        for (int i = 0; i < theInput.length; i++) {
            if (theInput[i] > max)
                max = theInput[i];
            if (theInput[i] < min)
                min = theInput[i];
        }
        double r = max - min;
        double a = 255.0 / r;
        double b = -a * min;
        int opaque = 255;
        for (int i = 0; i < data.length; i++) {
            int temp = (int) (theInput[i] * a + b);
            data[i] = (opaque << 24) | (temp << 16) | (temp << 8) | temp;
        }
        return data;

    }

}
