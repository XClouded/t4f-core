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

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class LogPoolerThread extends Thread {

    private List entries;
    private LogPooler log; // used for callbacks

    public LogPoolerThread(List entries, LogPooler log) {
        this.entries = entries;
        this.log = log;
    }

    public void run() {

        String entry;

        while (true) {

            synchronized (entries) {
                while (entries.size() == 0) {
                    if (log.isFinished())
                        return;
                    try {
                        entries.wait();
                    }
                    catch (InterruptedException e) {
                    }
                }
                entry = (String) entries.remove(entries.size() - 1);
            }

            int index = entry.indexOf(' ', 0);
            String remoteHost = entry.substring(0, index);
            String theRest = entry.substring(index, entry.length());

            try {
                remoteHost = InetAddress.getByName(remoteHost).getHostName();
            }
            catch (Exception e) {
                // remoteHost remains inputStreamdotted quad format
            }

            try {
                log.log(remoteHost + theRest);
            }
            catch (IOException e) {
            }
            
            yield();

        }

    }

}
