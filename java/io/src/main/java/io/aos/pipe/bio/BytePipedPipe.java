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
package io.aos.pipe.bio;

import io.aos.in.bio.GrepByteIn;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * This class demonstrates how you might use these pipe classes. It is another
 * implementation of a Unix-like grep command. Note that it frivolously passes
 * the output of the grep pipe through two rot13 pipes (which, combined, leave
 * the output unchanged). With the source, filter, and sink infrastructure
 * defined above, it is easy to define new filters and create pipes to perform
 * many useful operations. Other filter possibilities include sorting lines,
 * removing duplicate lines, and doing search-and-replace.
 */
public class BytePipedPipe {

    public static void main(String... args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java Pipes <pattern> <filename>");
            System.exit(0);
        }

        // Create a source, three filters, and a sink for the pipe.
        PipeSource source = new StreamPipeSource(new FileInputStream(args[1]));
        PipeFilter filter = new GrepFilter(args[0]);
        PipeFilter filter2 = new Rot13Filter();
        PipeFilter filter3 = new Rot13Filter();
        PipeSink sink = new StreamPipeSink(System.out);

        // Connect them all up.
        source.connectOutputTo(filter);
        filter.connectOutputTo(filter2);
        filter2.connectOutputTo(filter3);
        filter3.connectOutputTo(sink);

        // And start their threads running.
        source.start();
    }

}

/**
 * This interface defines the methods that the source of data for a pipe must
 * define. These methods are used to allow the source to easily be connected to
 * a sink. / Note that a PipeSource is Runnable--it is a thread body.
 */
interface PipeSource extends Runnable {
    abstract PipedOutputStream getPipedOutputStream();

    abstract void connectOutputTo(PipeSink sink) throws IOException;

    abstract PipeSink getSink();

    abstract void start();
}

/**
 * This interface defines the methods required for a sink of data from a pipe.
 * The methods are used when connecting a source to the sink. A PipeSink is also
 * Runnable.
 */
interface PipeSink extends Runnable {
    abstract PipedInputStream getPipedInputStream();

    abstract void connectInputTo(PipeSource source) throws IOException;
}

/**
 * A filter in a pipe behaves like both a source of data and a sink of data.
 */
interface PipeFilter extends PipeSource, PipeSink {
};

/**
 * This is an implementation of the PipeSource interface that uses an
 * InputStream as the source of data. It creates a PipedOutputStream to write
 * the data to. The run() method copies data from the stream to the pipe.
 */
class StreamPipeSource implements PipeSource {
    protected PipedOutputStream outputStream = new PipedOutputStream();
    protected InputStream inputStream;
    protected PipeSink sink;

    public StreamPipeSource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public PipedOutputStream getPipedOutputStream() {
        return outputStream;
    }

    public PipeSink getSink() {
        return sink;
    }

    public void connectOutputTo(PipeSink sink) throws IOException {
        this.sink = sink;
        outputStream.connect(sink.getPipedInputStream());
        sink.connectInputTo(this);
    }

    public void start() {
        // Start ourselves
        new Thread(this).start();

        // If our output is a filter, then call its start() method.
        // otherwise, just start our sink
        if (sink instanceof PipeFilter)
            ((PipeFilter) sink).start();
        else
            new Thread(sink).start();
    }

    public void run() {
        byte[] buffer = new byte[512];
        int bytes_read;
        try {
            for (;;) {
                bytes_read = inputStream.read(buffer);
                if (bytes_read == -1) {
                    return;
                }
                outputStream.write(buffer, 0, bytes_read);
            }
        }
        catch (IOException e) {
            if (e instanceof EOFException)
                return;
            else
                System.out.println(e);
        }
        finally {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                ;
            }
        }
    }
}

/**
 * This is an implementation of the PipeSink interface that uses an output
 * stream as the source of data. It creates a PipedInputStream to read data from
 * the pipe. The run() method reads data from that pipe and writes it to the
 * output stream (which might be a file or System.out, e.g.)
 */
class StreamPipeSink implements PipeSink {
    protected PipedInputStream inputStream = new PipedInputStream();
    protected OutputStream outputStream;

    public StreamPipeSink(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public PipedInputStream getPipedInputStream() {
        return inputStream;
    }

    public void connectInputTo(PipeSource source) throws IOException {
        inputStream.connect(source.getPipedOutputStream());
    }

    public void run() {
        byte[] buffer = new byte[512];
        int bytes_read;
        try {
            for (;;) {
                bytes_read = inputStream.read(buffer);
                if (bytes_read == -1)
                    return;
                outputStream.write(buffer, 0, bytes_read);
            }
        }
        catch (IOException e) {
            if (e instanceof EOFException)
                return;
            else
                System.out.println(e);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                ;
            }
        }
    }
}

/**
 * This is an abstract implementation of the PipeFilter interface. It creates
 * both a PipedInputStream to read from and a PipedOutputStream to write to. The
 * abstract method filter() needs to be defined by a subclass to read data from
 * one pipe, filter it, and write the filtered data to the other pipe.
 */
abstract class BasicPipeFilter implements PipeFilter {
    protected PipedInputStream inputStream = new PipedInputStream();
    protected PipedOutputStream outputStream = new PipedOutputStream();
    protected PipeSink sink;

    public PipedInputStream getPipedInputStream() {
        return inputStream;
    }

    public PipedOutputStream getPipedOutputStream() {
        return outputStream;
    }

    public void connectOutputTo(PipeSink sink) throws IOException {
        this.sink = sink;
        outputStream.connect(sink.getPipedInputStream());
        sink.connectInputTo((PipeSource) this);
    }

    public void start() {
        // Start ourselves
        new Thread(this).start();

        // If our output is a filter, then call its start() method.
        // otherwise, just start our sink
        if (sink instanceof PipeFilter) {
            ((PipeFilter) sink).start();
        }
        else {
            new Thread(sink).start();
        }
    }

    public PipeSink getSink() {
        return sink;
    }

    public void connectInputTo(PipeSource source) throws IOException {
        inputStream.connect(source.getPipedOutputStream());
    }

    public void run() {
        try {
            filter();
        }
        catch (IOException e) {
            if (e instanceof EOFException)
                return;
            else
                System.out.println(e);
        }
        finally {
            try {
                outputStream.close();
                inputStream.close();
            }
            catch (IOException e) {
            }
        }
    }

    abstract public void filter() throws IOException;
}

/**
 * This is a non-abstract implementation of the PipeFilter interface. It uses
 * the GrepInputStream we defined elsewhere to do the filtering.
 */
class GrepFilter extends BasicPipeFilter {
    protected GrepByteIn gis;
    protected PrintStream pout = new PrintStream(outputStream);

    public GrepFilter(String pattern) {
        gis = new GrepByteIn(new DataInputStream(inputStream), pattern);
    }

    public void filter() throws IOException {
        String line;
        for (;;) {
            line = gis.readLine();
            if (line == null)
                return;
            pout.println(line);
        }
    }
}

/**
 * This is another implementation fo PipeFilter. It implements the trival rot13
 * cypher on the letters A-Z and a-z.
 */
class Rot13Filter extends BasicPipeFilter {
    public void filter() throws IOException {
        byte[] buffer = new byte[512];
        int bytes_read;

        for (;;) {
            bytes_read = inputStream.read(buffer);
            if (bytes_read == -1)
                return;
            for (int i = 0; i < bytes_read; i++) {
                if ((buffer[i] >= 'a') && (buffer[i] <= 'z')) {
                    buffer[i] = (byte) ('a' + ((buffer[i] - 'a') + 13) % 26);
                }
                if ((buffer[i] >= 'A') && (buffer[i] <= 'Z')) {
                    buffer[i] = (byte) ('A' + ((buffer[i] - 'A') + 13) % 26);
                }
            }
            outputStream.write(buffer, 0, bytes_read);
        }
    }

}
