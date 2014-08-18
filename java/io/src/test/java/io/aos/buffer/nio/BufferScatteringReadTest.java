package io.aos.buffer.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

public class BufferScatteringReadTest {

    public void testScatteringRead(ScatteringByteChannel channel) throws IOException {

        ByteBuffer header = ByteBuffer.allocate(8);
        ByteBuffer body   = ByteBuffer.allocate(128);

        ByteBuffer[] buffers = { header, body };

        channel.read(buffers);

    }

}
