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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipesPipe2Test {

    public static void main(String... args) throws IOException {
        PipedOutputStream postream = new PipedOutputStream();
        PipedInputStream pistream = new PipedInputStream(postream);

        DataThread dt = new DataThread(postream);
        dt.start();

        DataInputStream distream = new DataInputStream(pistream);
        String s1 = distream.readLine();
        distream.close();

        System.out.print("\nData toggled: ");
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            if (Character.isUpperCase(ch))
                System.out.print(Character.toLowerCase(ch));
            else if (Character.isLowerCase(ch))
                System.out.print(Character.toUpperCase(ch));
            else
                System.out.print(s1.charAt(i));
        }
    }
}

class DataThread extends Thread {
    String str = "AbCd*EfG hIjKl, Mn";
    OutputStream ostream1;

    public DataThread(OutputStream ostream2) {
        ostream1 = ostream2;
    }

    public void run() { // purpose is to write to one end of pipe
        try {
            System.out.println("Original Data: " + str);
            DataOutputStream dostream = new DataOutputStream(ostream1);
            dostream.writeBytes(str);
            dostream.close();
        } catch (IOException e) {
            System.err.println("I/O problem occurred. " + e);
        }
    }
}
