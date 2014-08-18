package io.aos.buffer.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;

public class BufferGatheringWriteTest {

    public void gatheringWrite(GatheringByteChannel channel) throws IOException {

        ByteBuffer header = ByteBuffer.wrap("YourHeader".getBytes());
        ByteBuffer body   = ByteBuffer.wrap("YourBody".getBytes());

        ByteBuffer[] buffers = { header, body };

        channel.write(buffers);
    
    }

}
