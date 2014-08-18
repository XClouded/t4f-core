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
package io.aos.pipe.bio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipesPipeTest {

    public static void main(String... args) throws Exception {

        final PipedOutputStream pos = new PipedOutputStream();
        final PipedInputStream pis = new PipedInputStream(pos);

        Runnable runOutput = new Runnable() {
            public void run() {
                writeData(pos);
            }
        };

        Thread outThread = new Thread(runOutput, "outThread");
        outThread.start();

        Runnable runInput = new Runnable() {
            public void run() {
                readData(pis);
            }
        };

        Thread inThread = new Thread(runInput, "inThread");
        inThread.start();
    }

    private static void writeData(OutputStream os) {
        try {
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(os));

            int[] numArray = { 1, 2, 3, 4, 5 };

            for (int i = 0; i < numArray.length; i++) {
                outputStream.writeInt(numArray[i]);
            }

            outputStream.flush();

            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readData(InputStream is) {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(is));
        boolean eof = false;
        try {
            while (!eof) {
                int iValue = inputStream.readInt();
                System.out.println("read value = " + iValue);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End of Data");
    }
    
}
