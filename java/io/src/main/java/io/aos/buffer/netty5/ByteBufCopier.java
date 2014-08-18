package io.aos.buffer.netty5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufCopier {
    
    public static String copy(String text) {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer(text, utf8);
        ByteBuf copy = buf.copy(0, text.length());
        return copy.toString(utf8);
    }
 
}
