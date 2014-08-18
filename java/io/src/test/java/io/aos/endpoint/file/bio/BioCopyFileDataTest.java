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
package io.aos.endpoint.file.bio;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BioCopyFileDataTest {

    @Test
    public void copy(String source_name, String dest_name) throws IOException {
        File source_file = new File(source_name);
        File destination_file = new File(dest_name);
        FileInputStream source = null;
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;

        try {
            // First make sure the specified source file
            // exists, is a file, and is readable.
            if (!source_file.exists() || !source_file.isFile())
                throw new FileCopyException("FileCopy: no such source file: " + source_name);
            if (!source_file.canRead())
                throw new FileCopyException("FileCopy: source file " + "is unreadable: " + source_name);

            // If the destination exists, make sure it is a writeable file
            // and ask before overwriting it. If the destination doesn't
            // exist, make sure the directory exists and is writeable.
            if (destination_file.exists()) {
                if (destination_file.isFile()) {
                    DataInputStream inputStream = new DataInputStream(System.in);
                    String response;

                    if (!destination_file.canWrite())
                        throw new FileCopyException("FileCopy: destination " + "file is unwriteable: " + dest_name);

                    System.out.print("File " + dest_name + " already exists.  Overwrite? (Y/N): ");
                    System.out.flush();
                    response = inputStream.readLine();
                    if (!response.equals("Y") && !response.equals("y"))
                        throw new FileCopyException("FileCopy: copy cancelled.");
                } else
                    throw new FileCopyException("FileCopy: destination " + "is not a file: " + dest_name);
            } else {
                File parentdir = parent(destination_file);
                if (!parentdir.exists())
                    throw new FileCopyException("FileCopy: destination " + "directory doesn't exist: " + dest_name);
                if (!parentdir.canWrite())
                    throw new FileCopyException("FileCopy: destination " + "directory is unwriteable: " + dest_name);
            }

            // If we've gotten this far, then everything is okay; we can
            // copy the file.
            source = new FileInputStream(source_file);
            destination = new FileOutputStream(destination_file);
            buffer = new byte[1024];
            while (true) {
                bytes_read = source.read(buffer);
                if (bytes_read == -1)
                    break;
                destination.write(buffer, 0, bytes_read);
            }
        }
        // No matter what happens, always close any streams we've opened.
        finally {
            if (source != null)
                try {
                    source.close();
                } catch (IOException e) {
                    // Do nothing...
                    e.printStackTrace();
                }
            if (destination != null)
                try {
                    destination.close();
                } catch (IOException e) {
                    ;
                }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        OutputStream outputStream = new FileOutputStream(dest);
        byte[] buffer = new byte[(int) source.length()];
        FileInputStream inputStream = new FileInputStream(source);
        inputStream.read(buffer);
        try {
            outputStream.write(buffer);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    // File.getParent() can return null when the file is specified without
    // a directory or is inputStreamthe root directory.
    // This method handles those cases.
    private static File parent(File f) {
        String dirname = f.getParent();
        if (dirname == null) {
            if (f.isAbsolute())
                return new File(File.separator);
            else
                return new File(System.getProperty("user.dir"));
        }
        return new File(dirname);
    }

}

class FileCopyException extends IOException {
    private static final long serialVersionUID = 1L;

    public FileCopyException(String msg) {
        super(msg);
    }
}
