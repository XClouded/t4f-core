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
package com.kdgregory.example.bytebuffer.jni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;


/**
 *  Attaches to a shared memory block and prompts the user for input. Blank
 *  lines simply print the current contents of the block. Non-blank lines print
 *  the contents of the block and replace it with the input.
 *  <p>
 *  Invocation: <code>Main SHMKEY</code>
 *  <p>
 *  where: <code>SHMKEY</code> is a numeric key used to access the shared memory
 *  block.
 */
public class Main
{
    public static void main(String... argv)
    throws Exception
    {
        if (argv.length != 1)
        {
            System.err.println("invocation: Main SHMKEY");
            System.exit(1);
        }

        SharedMem shmem = new SharedMem(Integer.parseInt(argv[0]), 16384);
        try
        {
            repl(shmem.getBuffer().asCharBuffer());
        }
        finally
        {
            shmem.close();
        }
    }


    private static void repl(CharBuffer buf)
    throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            System.out.print("> ");
            String input = inputStream.readLine();
            if (input == null)
                return;

            buf.rewind();
            String current = buf.toString().trim();
            System.out.println(current);

            if (input.length() > 0)
            {
                // it would be nice if "clear()" actually cleared the buffer ...
                buf.clear();
                for (int ii = 0 ; ii < buf.capacity() ; ii++)
                    buf.put('\0');

                buf.clear();
                buf.put(input);
                System.out.println(input);
            }
        }
    }
}
