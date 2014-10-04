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
package io.aos.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AosProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(AosProcess.class);

    public static String exec(String command, boolean waitForResponse) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        pb.redirectErrorStream(true);
        LOGGER.info("Linux command=" + command);
        String response = null;
        try {
            Process shell = pb.start();
            if (waitForResponse) {
                InputStream shellIn = shell.getInputStream();
                int shellExitStatus = shell.waitFor();
                LOGGER.info("Shell exit status=" + shellExitStatus);
                response = asString(shellIn);
                shellIn.close();
                if (shellExitStatus != 0) {
                    throw new RuntimeException("Shell exit status=" + shellExitStatus + " - returned string=" + response);
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("Error occured while executing command." + e);
            throw new RuntimeException("Error occured while executing command." + e);
        }
        catch (InterruptedException e) {
            LOGGER.error("Error occured while executing command." + e);
            throw new RuntimeException("Error occured while executing command." + e);
        }
        return response;
    }

    private static String asString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            }
            finally {
                is.close();
            }
            return writer.toString();
        }
        return null;
    }

}
