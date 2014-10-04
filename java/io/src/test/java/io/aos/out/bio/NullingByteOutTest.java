package io.aos.out.bio;

import io.aos.out.bio.NullingByteOut;

import java.io.IOException;

import org.junit.Test;

public class NullingByteOutTest {

    @Test
    public void testNull() throws IOException {
        NullingByteOut nullingOut = new NullingByteOut();
        nullingOut.write(3);
        nullingOut.close();
    }

}
