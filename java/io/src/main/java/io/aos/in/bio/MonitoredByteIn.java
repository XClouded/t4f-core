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
package io.aos.in.bio;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

public class MonitoredByteIn {

    public static void read(InputStream inputStream, String name, int length) throws IOException, InterruptedException {

        ProgressMonitorInputStream pinputStream = new ProgressMonitorInputStream(null, name, inputStream);

        // Set the maximum value of the ProgressMonitor
        ProgressMonitor pm = pinputStream.getProgressMonitor();
        pm.setMaximum(length);

        // Read the data
        for (int c = pinputStream.read(); c != -1; c = pinputStream.read()) {
            System.out.print((char) c);
        }
        pinputStream.close();

        TimeUnit.SECONDS.sleep(10);

    }

}
