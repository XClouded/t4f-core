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
 * @(#)Tracker.java	1.8 10/03/23
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.hprof;

/* This class and it's methods are used by 5.0 hprof when injecting bytecodes
 *   into class file images.
 *   WARNING: This class may not be used by newer versions of hprof.
 *   See the directory src/share/demo/jvmti/hprof and the file README.txt
 *   for more details.
 */

public class Tracker {
 
    /* Master switch that activates calls to native functions. */
    
    private static int engaged = 0; 
  
    /* To track memory allocated, we need to catch object init's and arrays. */
    
    /* At the beginning of java.jang.Object.<init>(), a call to
     *   sun.tools.hprof.Tracker.ObjectInit() is injected.
     */

    private static native void nativeObjectInit(Object thr, Object obj);
    
    public static void ObjectInit(Object obj)
    {
	if ( engaged != 0 ) {
	    nativeObjectInit(Thread.currentThread(), obj);
	}
    }
    
    /* Immediately following any of the newarray bytecodes, a call to
     *   sun.tools.hprof.Tracker.NewArray() is injected.
     */

    private static native void nativeNewArray(Object thr, Object obj);
   
    public static void NewArray(Object obj)
    {
	if ( engaged != 0 ) {
	    nativeNewArray(Thread.currentThread(), obj);
	}
    }
   
    /* For cpu time spent in methods, we need to inject for every method. */

    /* At the very beginning of every method, a call to
     *   sun.tools.hprof.Tracker.CallSite() is injected.
     */

    private static native void nativeCallSite(Object thr, int cnum, int mnum);
    
    public static void CallSite(int cnum, int mnum)
    {
	if ( engaged != 0 ) {
	    nativeCallSite(Thread.currentThread(), cnum, mnum);
	}
    }
    
    /* Before any of the return bytecodes, a call to
     *   sun.tools.hprof.Tracker.ReturnSite() is injected.
     */

    private static native void nativeReturnSite(Object thr, int cnum, int mnum);
    
    public static void ReturnSite(int cnum, int mnum)
    {
	if ( engaged != 0 ) {
	    nativeReturnSite(Thread.currentThread(), cnum, mnum);
	}
    }
    
}

