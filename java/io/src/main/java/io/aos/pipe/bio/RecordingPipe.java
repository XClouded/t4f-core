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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RecordingPipe {

    public static void main(String... args) {

        if (args.length < 2) {
            System.err.println("Usage: java StreamRecoder " + "infile_encoding outfile_encoding infile outfile");
            return;
        }

        InputStreamReader isr = null;
        OutputStreamWriter osw = null;

        try {
            File infile = new File(args[2]);
            File outfile = new File(args[3]);

            if (outfile.exists() && infile.getCanonicalPath().equals(outfile.getCanonicalPath())) {
                System.err.println("Can't convert file inputStreamplace");
                return;
            }

            FileInputStream fis = new FileInputStream(infile);
            FileOutputStream fout = new FileOutputStream(outfile);
            isr = new InputStreamReader(fis, args[0]);
            osw = new OutputStreamWriter(fout, args[1]);

            while (true) {
                int c = isr.read();
                if (c == -1)
                    break; // end of stream
                osw.write(c);
            }
            osw.close();
            isr.close();
        }
        catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
        finally {
            if (isr != null) {
                try {
                    isr.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
