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
package io.aos.primitive;

public class PrimitiveConvertion {

    public static void main(String... args) {

        // Integer to String :
        int i = 42;
        long l = 4343;
        float f = 0.4343F;
        String str = Integer.toString(i);
        // or String str = "" + i

        // double to String :
        str = Double.toString(i);

        // long to String :
        str = Long.toString(l);

        // float to String :
        str = Float.toString(f);

        // String to integer :
        str = "25";
        i = Integer.valueOf(str).intValue();
        // or
        i = Integer.parseInt(str);

        // String to double :
        double d = Double.valueOf(str).doubleValue();

        // String to long :
        l = Long.valueOf(str).longValue();
        // or
        l = Long.parseLong(str);

        // String to float :
        f = Float.valueOf(str).floatValue();

        // decimal to binary :
        i = 42;
        String binstr = Integer.toBinaryString(i);

        // decimal to hexadecimal :
        i = 42;
        String hexstr = Integer.toString(i, 16);
        // or String hexstr = Integer.toHexString(i);
        // or (with leading zeroes and uppercase)
        // hexadecimal (String) to integer :
        i = Integer.valueOf("B8DA3", 16).intValue();
        // or
        i = Integer.parseInt("B8DA3", 16);

        // ASCII code to String
        i = 64;
        String aChar = new Character((char) i).toString();

        // integer to ASCII code (byte)
        char c = 'A';
        i = (int) c; // i will have the value 65 decimal

        // To extract Ascii codes from a String
        String test = "ABCD";
        for (i = 0; i < test.length(); ++i) {
            c = test.charAt(i);
            int j = (int) c;
            System.out.println(j);
        }

        // integer to boolean
        boolean b = (i != 0);

        // boolean to integer
        i = (b) ? 1 : 0;

        // note :To catch illegal number conversion, try using the try/catch
        // mechanism.

        try {
            i = Integer.parseInt(new String("1231'"));
        } catch (NumberFormatException e) {
        }

        i = 42;
        System.out.print(Integer.toHexString(0x10000 | i).substring(1).toUpperCase());
    }

}
