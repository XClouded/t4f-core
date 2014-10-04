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
package io.aos.charset;

import java.io.IOException;

import org.junit.Test;

public class CharsetAsciiTest {

    @Test
    public void testPrint1() {
        for (int i = 32; i < 127; i++) {
            System.out.write(i);
            if (i % 8 == 7) {
                System.out.write('\n');
            } else {
                System.out.write('\t');
            }
        }
        System.out.write('\n');
    }

    @Test
    public void testntPri2() {
        byte[] b = new byte[(127 - 31) * 2];
        int index = 0;
        for (int i = 32; i < 127; i++) {
            b[index++] = (byte) i;
            // Break line after every eight characters.
            if (i % 8 == 7)
                b[index++] = (byte) '\n';
            else
                b[index++] = (byte) '\t';
        }
        b[index++] = (byte) '\n';
        try {
            System.out.write(b);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
