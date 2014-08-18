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
package io.aos.crypto.spl09;

import java.io.*;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;

/**
 * CMSProcessable that handles File objects.
 */
public class CMSProcessableFile
    implements CMSProcessable
{
    private File file;
    private static final int BUF_SIZE = 4096;
    
    /**
     * Base constructor.
     * 
     * @param file a File object representing the file we want processed.
     */
    public CMSProcessableFile(
        File file)
    {
        this.file = file;
    }
    
    /**
     * Write the contents of the file to the passed in OutputStream
     * 
     * @param out the OutputStream passed in by the CMS API.
     */
    public void write(
        OutputStream out)
        throws IOException, CMSException
    {
        FileInputStream fIn = new FileInputStream(file);
        byte[]          buf = new byte[BUF_SIZE];
        
        int count = 0;
        while ((count = fIn.read(buf)) > 0)
        {
            out.write(buf, 0, count);
        }
        
        fIn.close();
    }

    /**
     * Return the File object we were created with.
     */
    public Object getContent()
    {
        return file;
    }
}
