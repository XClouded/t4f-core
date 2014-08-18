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
package io.aos.concurrent.count;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAll {
    public static void main(String... args) {
    Pattern pattern = Pattern.compile(args[0]);
    String text = args[1];

    List<MatchResult> results = findAll(pattern, text);
    for(MatchResult r : results) {
        System.out.printf("Found '%s' at (%d,%d)%n",
                  r.group(), r.start(), r.end());
    }
    }

    public static List<MatchResult> findAll(Pattern pattern, CharSequence text)
    {
    List<MatchResult> results = new ArrayList<MatchResult>();
    Matcher m = pattern.matcher(text);
    while(m.find()) results.add(m.toMatchResult());
    return results;
    }
}
