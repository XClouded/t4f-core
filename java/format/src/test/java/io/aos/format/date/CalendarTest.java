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
package io.aos.format.date;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class CalendarTest {

    /**
     * // format => Mon, 13 Feb 2006 12:00 GMT
     */
    @Test
    public static void testDateFormattedByCalendarSetValue() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
        // ! set -1 to create correct month !
        c.set(2006, 7, 9);
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:MM", Locale.US);
        String dateFormatted = bartDateFormat.format(c.getTime());
        dateFormatted += " GMT";
        System.out.println(dateFormatted);
    }

    @Test
    public static void testDateFormattedByCalendarSetTime() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
        Date dateTest = new Date(System.currentTimeMillis());
        c.setTime(dateTest);
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:MM", Locale.US);
        String dateFormatted = bartDateFormat.format(c.getTime());
        dateFormatted += " GMT";
        System.out.println(dateFormatted);
    }

}
