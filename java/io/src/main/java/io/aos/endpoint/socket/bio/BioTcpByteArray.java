package io.aos.endpoint.socket.bio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BioTcpByteArray {

    public byte[] copy(InputStream is) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        
        is.close();

        return buffer.toByteArray();

    }

}
