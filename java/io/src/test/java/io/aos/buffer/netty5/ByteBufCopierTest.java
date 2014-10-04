package io.aos.buffer.netty5;

import static org.junit.Assert.assertEquals;
import io.aos.buffer.netty5.ByteBufCopier;

import org.junit.Test;

public class ByteBufCopierTest {
    
    @Test
    public void testCopy() {
        String s1 = "Netty ByteBuf rocks!";
        String s2 = ByteBufCopier.copy(s1);
        assertEquals(s1, s2);
    }

}
