package io.aos.endpoint.socket.nio.fwk2;
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
 * @(#)StringContent.java	1.5 10/03/23
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

import java.io.*;
import java.nio.*;
import java.nio.charset.*;

/**
 * A Content type that provides for transferring Strings.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 * @version 1.5, 10/03/23
 */
class StringContent implements Content {

    private static Charset ascii = Charset.forName("US-ASCII");

    private String type;		// MIME type
    private String content;

    StringContent(CharSequence c, String t) {
	content = c.toString();
	if (!content.endsWith("\n"))
	    content += "\n";
	type = t + "; charset=iso-8859-1";
    }

    StringContent(CharSequence c) {
	this(c, "text/plain");
    }

    StringContent(Exception x) {
	StringWriter sw = new StringWriter();
	x.printStackTrace(new PrintWriter(sw));
	type = "text/plain; charset=iso-8859-1";
	content = sw.toString();
    }

    public String type() {
	return type;
    }

    private ByteBuffer bb = null;

    private void encode() {
	if (bb == null)
	    bb = ascii.encode(CharBuffer.wrap(content));
    }

    public long length() {
	encode();
	return bb.remaining();
    }

    public void prepare() {
	encode();
	bb.rewind();
    }

    public boolean send(ChannelIO cio) throws IOException {
	if (bb == null)
	    throw new IllegalStateException();
	cio.write(bb);

	return bb.hasRemaining();
    }

    public void release() throws IOException {
    }
}
