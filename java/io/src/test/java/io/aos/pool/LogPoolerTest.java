package io.aos.pool;

import io.aos.pool.LogPooler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.Test;

public class LogPoolerTest {

    @Test
    @Ignore
    public void test() throws FileNotFoundException {
        LogPooler logPooler = new LogPooler(new FileInputStream("./pom.xml"), System.out, 100);
        logPooler.processLogFile();
    }

}
