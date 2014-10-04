package io.aos.buffer.netty5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

public class ByteBufAllocate {

    public static void assign() {

        ByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        ByteBuf buf = alloc.heapBuffer(1024);
        byte[] array = buf.array();
        int arrayOffset = buf.arrayOffset();
        for (int i = 0; i < buf.capacity(); i++) {
            byte b = array[i + arrayOffset];
            // ...
        }
        // or ...
        int start = arrayOffset;
        int end = buf.capacity() + buf.arrayOffset();
        for (int i = start; i < end; i++) {
            byte b = array[i];
            // ...
        }
        // then release explicitly.
        buf.release();

    }

}
