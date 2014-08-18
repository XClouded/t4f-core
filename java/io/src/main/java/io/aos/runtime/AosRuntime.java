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
package io.aos.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AosRuntime {
    private static final Logger LOGGER = LoggerFactory.getLogger(AosRuntime.class);

    public static String exec(String command) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("bash -c " + command);
        StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");
//        errorGobbler.start();
        StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT");
//        outputGobbler.start();
        int exitCode = proc.waitFor();
        LOGGER.info("ExitValue: " + exitCode);
        if (exitCode != 0) {
            throw new RuntimeException("Shell exit status=" + exitCode + " - returned error=" + errorGobbler.gobbled());
        }
        return outputGobbler.gobbled();
    }

    private static class StreamGobbler extends Thread {
        private InputStream is;
        private String type;
        private StringBuilder sb;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
            this.sb = new StringBuilder();
        }

        public void run() {
//            try {
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    LOGGER.info(type + "> " + line);
//                    sb.append(line);
//                    sb.append("\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("Exception while gobbling type=" + type, e);
//            }
        }
        
        public String gobbled() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    LOGGER.info(type + "> " + line);
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Exception while gobbling type=" + type, e);
            }
            return sb.toString();
        }
        
    }
}
