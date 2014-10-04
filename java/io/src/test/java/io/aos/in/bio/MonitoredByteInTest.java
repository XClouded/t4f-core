package io.aos.in.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class MonitoredByteInTest {
    
    @Test
    public void test() throws IOException, InterruptedException {
        
        URL u = new URL("http://www.google.com");
        URLConnection uc = u.openConnection();
        InputStream inputStream = uc.getInputStream();
        
        MonitoredByteIn.read(inputStream, u.toString(), uc.getContentLength());

        // Since we brought up a GUI, we have to explicitly exit here
        // rather than simply returning from the main() method.
        System.exit(0);

    }

}
