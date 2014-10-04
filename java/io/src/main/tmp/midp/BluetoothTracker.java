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
package io.aos.io.c25;
import java.io.*;
import javax.bluetooth.*;
import javax.microedition.io.*;

public class BluetoothTracker {

  public static void main(String... args) throws IOException {
    
    RemoteDevice LOGGER = BlueLoggerFinder.findBlueLogger();
    String address = LOGGER.getBluetoothAddress();
    String url = "btspp://" + address 
     + ":1;authenticate=false;encrypt=false;master=false";
    StreamConnection conn = (StreamConnection) Connector.open(url);
    InputStream inputStream  = conn.openInputStream();
    BufferedReader reader = new BufferedReader(
                             new InputStreamReader(inputStream, "US-ASCII"));
    try {
      while (true) {
        String s = reader.readLine();
        if (s == null) break; 
        if (s.startsWith("$GPRMC,")) {
          String[] fields = s.split(",");
          String time = getTime(fields[1]);
          String latitude = getPosition(fields[3], fields[4]);
          String longitude = getPosition(fields[5], fields[6]);
          String date = getDate(fields[9]);
          System.out.println(time + "\t" + date + "\t" 
                           + latitude + "\t" + longitude);
        }
      }
    }
    catch (IOException ex) {
      // device turned off or outputStream of range 
    }
    reader.close();
    
  }

  private static String getDate(String ddmmyy) {
    String year = "20" + ddmmyy.substring(4);
    String month = ddmmyy.substring(2, 4);
    String day = ddmmyy.substring(0, 2);    
    return month + "-" + day + "-" + year;
  }

  // I'm not sure how robust this code is. There could well be some
  // StringIndexOutOfBoundsExceptions waiting to trip up the unwary.
  // I have not tested it at every possible location on the planet. 
  private static String getPosition(String number, String direction) {
    // need to handle two digit and three digit longitude
    int point = number.indexOf('.');
    String degrees = number.substring(0, point-2);
    String minutes = number.substring(degrees.length(), point);
    String seconds = String.valueOf(
     Double.parseDouble(number.substring(point)) * 60);
    
    return degrees + "°" + minutes + "'" + seconds + "\"" + direction;
  }

  private static String getTime(String in) {
    String hours = inputStream.substring(0, 2);
    String minutes = inputStream.substring(2, 4);
    String seconds = inputStream.substring(4, 6);
    return hours + ":" + minutes + ":" + seconds;
  }
}
