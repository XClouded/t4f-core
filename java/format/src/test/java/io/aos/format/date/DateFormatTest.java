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

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class DateFormatTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void test1() {
        /*
         * on some JDK, the default TimeZone is wrong we must set the TimeZone
         * manually!!! Calendar cal =
         * Calendar.getInstance(TimeZone.getTimeZone("EST"));
         */
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        /*
         * * on some JDK, the default TimeZone is wrong* we must set the
         * TimeZone manually!!!* sdf.setTimeZone(TimeZone.getTimeZone("EST"));
         */
        sdf.setTimeZone(TimeZone.getDefault());

        System.out.println("Now : " + sdf.format(calendar.getTime()));

        calendar.add(Calendar.HOUR, 12);
        System.out.println("Now + 12 hours : " + sdf.format(calendar.getTime()));

    }

    @Test
    public void test2() {
        System.out.println(easyDateFormat("dd MMMMM yyyy"));
        System.out.println(easyDateFormat("yyyyMMdd"));
        System.out.println(easyDateFormat("dd.MM.yy"));
        System.out.println(easyDateFormat("MM/dd/yy"));
        System.out.println(easyDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z"));
        System.out.println(easyDateFormat("EEE, MMM d, ''yy"));
        System.out.println(easyDateFormat("h:mm a"));
        System.out.println(easyDateFormat("H:mm:ss:SSS"));
        System.out.println(easyDateFormat("K:mm a,z"));
        System.out.println(easyDateFormat("yyyy.MMMMM.dd GGG hh:mm aaa"));
    }

    @Test
    public void test3() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        assertNotNull(getHtmlExpiresDateFormatted().format(cal.getTime()));
    }

    @Test
    public void test4() throws ParseException {
        /*
         * * we specify Locale.US since months are in english * we want to parse
         * a TimeStamp
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
        /*
         * * fix timezone in the SimpleDateFormat * bug in JDK1.1
         */
        sdf.setCalendar(Calendar.getInstance());
        /*
         * * create a Date (no choice, parse returns a Date object)
         */
        Date d = sdf.parse("24-Feb-1998 17:39:35");
        System.out.println(d.toString());
        /*
         * * create a GregorianCalendar from a Date object
         */
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        System.out.println(gc.getTime().toString());

    }

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        print(calendar.getTime());
        calendar.add(Calendar.HOUR, 12);
        print(calendar.getTime());
    }
    
    private void print(Date date) {
        
        System.out.printf("two digit hour on a 24-hour clock: %tH/%TH\n", date, date);
        System.out.printf("two digit hour on a 12-hour clock: %tI/%TI\n", date, date);
        System.out.printf("one-or-two digit hour on a 24-hour clock: %tk/%Tk\n", date, date);
        System.out.printf("one-or-two digit hour on a 12-hour: %tl/%Tl\n", date, date);
        System.out.printf("two digit minutes ranging from 00 to 59: %tH/%TH\n", date, date);
        System.out.printf("two digit seconds ranging from 00 to 60 : %tS/%TS\n", date, date);
        System.out.printf("milliseconds: %tL/%TL\n", date, date);
        System.out.printf("nanoseconds: %tN/%TN\n", date, date);
        System.out.printf("Locale-specific morning/afternoon indicator: %tp/%Tp\n", date, date);
        System.out.printf("RFC 822 numeric time zone indicator: %tz/%Tz\n", date, date);
        System.out.printf("Time zone abbreviation: %tZ/%TZ\n", date, date);
        System.out.printf("seconds since the epoch: %ts/%Ts\n", date, date);
        System.out.printf("milliseconds since the epoch: %TQ\n", date);
        System.out.printf("localized month name: %tB/%TB\n", date, date);
        System.out.printf("localized, abbreviated month: %tb/%Tb\n", date, date);
        System.out.printf("localized, abbreviated month: %th/%Th\n", date, date);
        System.out.printf("localized day name: %tA/%TA\n", date, date);
        System.out.printf("localized, abbreviated day: %ta/%Ta\n", date, date);
        System.out.printf("two-digit century: %tC/%TC\n", date, date);
        System.out.printf("four digit year: %tY/%TY\n", date, date);
        System.out.printf("two-digit year: %ty/%Ty\n", date, date);
        System.out.printf("three-digit day of the year: %tj/%Tj\n", date, date);
        System.out.printf("two-digit month: %tm/%Tm\n", date, date);
        System.out.printf("two-digit day of the month: %td/%Td\n", date, date);
        System.out.printf("a one-or-two-digit day of the month: %te/%Te\n", date, date);
        System.out.printf("hours and minutes on a 24-hour clock: %tR/%TR\n", date, date);
        System.out.printf("hours, minutes, and seconds on a 24-hour clock: %tT/%TT\n", date, date);
        System.out.printf("hours, minutes, and seconds on a 12-hour clock: %tr/%Tr\n", date, date);
        System.out.printf("month/day/year: %tD/%TD\n", date, date);
        System.out.printf("ISO 8601 standard date: %tF/%TF\n", date, date);
        System.out.printf("Unix date format: %tc/%Tc\n", date, date);
        System.out.println();

    }

    private SimpleDateFormat getHtmlExpiresDateFormatted() {
        SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return httpDateFormat;
    }

    private String easyDateFormat(String format) {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String datenewformat = formatter.format(today);
        return datenewformat;
    }

}
