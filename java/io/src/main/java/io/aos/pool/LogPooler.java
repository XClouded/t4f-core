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
package io.aos.pool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LogPooler {
    private BufferedReader bufferedReader;
    private BufferedWriter buffereWriter;
    private int numberOfThreads;
    private List entries = Collections.synchronizedList(new LinkedList());
    private boolean finished = false;
    private int test = 0;

    public LogPooler(InputStream is, OutputStream outputStream, int numberOfThreads) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(is));
        this.buffereWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        this.numberOfThreads = numberOfThreads;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void processLogFile() {

        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new LogPoolerThread(entries, this);
            t.start();
        }

        try {

            String entry = null;
            while ((entry = bufferedReader.readLine()) != null) {

                if (entries.size() > numberOfThreads) {
                    try {
                        Thread.sleep((long) (1000.0 / numberOfThreads));
                    }
                    catch (InterruptedException e) {
                    }
                    continue;
                }

                synchronized (entries) {
                    entries.add(0, entry);
                    entries.notifyAll();
                }

                Thread.yield();

            }

        }
        catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        this.finished = true;

        // finish any threads that are still waiting
        synchronized (entries) {
            entries.notifyAll();
        }

    }

    public void log(String entry) throws IOException {
        buffereWriter.write(entry + System.getProperty("line.separator", "\r\n"));
        buffereWriter.flush();
    }

}
