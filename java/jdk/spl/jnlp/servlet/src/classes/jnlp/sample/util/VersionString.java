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
/*
 * @(#)VersionString.java	1.8 10/03/24
 * 
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Oracle or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package jnlp.sample.util;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * Utility class that knows to handle version strings
 * A version string is of the form:
 *
 *  (version-id ('+'?) ' ') *
 *
 */
public class VersionString {
    private ArrayList _versionIds;
    
    /** Constructs a VersionString object from string */
    public VersionString(String vs) {
	_versionIds = new ArrayList();
	if (vs != null) {
	    StringTokenizer st = new StringTokenizer(vs, " ", false);
	    while(st.hasMoreElements()) {
		// Note: The VersionID class takes care of a postfixed '+'
		_versionIds.add(new VersionID(st.nextToken()));
	    }
	}
    }
    
    /** Check if this VersionString object contains the VersionID m */
    public boolean contains(VersionID m) {
	for(int i = 0; i < _versionIds.size(); i++) {
	    VersionID vi = (VersionID)_versionIds.get(i);
	    boolean check = vi.match(m);
	    if (check) return true;
	}
	return false;
    }
       
    /** Check if this VersionString object contains the VersionID m, given as a string */
    public boolean contains(String versionid) {
	return contains(new VersionID(versionid));
    }
    
    /** Check if this VersionString object contains anything greater than m */
    public boolean containsGreaterThan(VersionID m) {
        for(int i = 0; i < _versionIds.size(); i++) {
            VersionID vi = (VersionID)_versionIds.get(i);
            boolean check = vi.isGreaterThan(m);
            if (check) return true;
        }
        return false;
    }   
  
    /** Check if this VersionString object contains anything greater than the VersionID m, given as a string */
    public boolean containsGreaterThan(String versionid) {
	return containsGreaterThan(new VersionID(versionid));
    }
    
    /** Check if the versionString 'vs' contains the VersionID 'vi' */
    static public boolean contains(String vs, String vi) {
	return (new VersionString(vs)).contains(vi);
    }

    /** Pretty-print object */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	for(int i = 0; i < _versionIds.size(); i++) {
	    sb.append(_versionIds.get(i).toString());
	    sb.append(' ');
	}
	return sb.toString();
    }
}



