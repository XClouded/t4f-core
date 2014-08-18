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
// Copyright (c) Keith D Gregory, all rights reserved
package com.kdgregory.example.bytebuffer;

import java.nio.ByteBuffer;


/**
 *  Uses a <code>ByteBuffer</code> to expose the fields of a TCP header (not that
 *  a Java program is ever likely to see one of those).
 */
public class TcpHeaderWrapper
{
    ByteBuffer buf;
    
    public TcpHeaderWrapper(byte[] data)
    {
        buf = ByteBuffer.wrap(data);
    }

    
    public short getSourcePort()
    {
        return buf.getShort(0);
    }
    
    public void setSourcePort(short value)
    {
        buf.putShort(0, value);
    }
    
    
    public short getDestPort()
    {
        return buf.getShort(2);
    }
    
    public void setDestPort(short value)
    {
        buf.putShort(2, value);
    }
    
    
    public int getSeqNum()
    {
        return buf.getInt(4);
    }
    
    public void setSeqNum(int value)
    {
        buf.putInt(4, value);
    }
    
    
    public int getAckNum()
    {
        return buf.getInt(8);
    }
    
    public void setAckNum(int value)
    {
        buf.putInt(8, value);
    }
    
    
    public short getFlags()
    {
        return buf.getShort(12);
    }
    
    public void setFlags(short value)
    {
        buf.putShort(12, value);
    }
    
    
    public short getWindowSize()
    {
        return buf.getShort(14);
    }
    
    public void setWindowSize(short value)
    {
        buf.putShort(14, value);
    }
    
    
    public short getChecksum()
    {
        return buf.getShort(16);
    }
    
    public void setChecksum(short value)
    {
        buf.putShort(16, value);
    }
    
    
    public short getUrgentPtr()
    {
        return buf.getShort(18);
    }
    
    public void setUrgentPtr(short value)
    {
        buf.putShort(18, value);
    }
    
    
    public byte[] getData()
    {
        buf.position(getDataOffset());
        int size = buf.remaining();
        byte[] data = new byte[size];
        buf.get(data);
        return data;
    }
    
    public ByteBuffer getDataAsBuffer()
    {
        buf.position(getDataOffset());
        return buf.slice();
    }
    
    public void setData(byte[] value)
    {
        buf.position(getDataOffset());
        buf.put(value);
    }
    
    
//----------------------------------------------------------------------------
//  Internals
//----------------------------------------------------------------------------
    
    /**
     *  The TCP header can contain a variable-length set of options after the
     *  fixed fields. The high-order 4 bits of the "flags" field gives the
     *  offset (in 4-byte increments) of the actual data.
     */
    private int getDataOffset()
    {
        int rawOffset = (getFlags() & 0xF000) >> 12;
        return rawOffset * 32;
    }
}
