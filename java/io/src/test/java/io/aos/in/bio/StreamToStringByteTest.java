package io.aos.in.bio;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import org.junit.Test;

/**
 * $ java -Xmx2024M StreamTostring.InputStreamtoString StringBuilder time :1276
 * $ java -Xmx2024M StreamTostring.InputStreamtoString StringWriter time: 876
 * $ java -Xmx2024M StreamTostring.InputStreamtoString Scanner time: 2212
 * 
 */
public class StreamToStringByteTest {

    public static void main(String[] args) throws IOException {

//        System.out.println(stringBuilder("/input.txt"));
//        System.out.println(stringWriter("/input.txt"));
//        System.out.println(scanner("/input.txt"));

        // Date start = new Date();
        // stringBuilder("/input.txt");
        // Date end = new Date();
        // System.out.println("StringBuilder time : "+(end.getTime()-start.getTime()));

        // Date start = new Date();
        // stringWriter("/input.txt");
        // Date end = new Date();
        // System.out.println("StringWriter time: " + (end.getTime() -
        // start.getTime()));

        // Date start = new Date();
        // scanner("/input.txt");
        // Date end = new Date();
        // System.out.println("Scanner time: " + (end.getTime() -
        // start.getTime()));
    }

    @Test
    public void test() throws IOException {
        InputStream is = new ByteArrayInputStream("file content..blah blah".getBytes());
        String result = asString(is);
        System.out.println(result);
    }

    @Test
    public void stringBuilder(String fileName) throws IOException {

        StringBuilder sbuilder = null;
        FileInputStream fStream = null;
        BufferedReader input = null;

        try {

            fStream = new FileInputStream(fileName);
            input = new BufferedReader(new InputStreamReader(fStream, "UTF-8"));

            sbuilder = new StringBuilder();

            String str = input.readLine();

            while (str != null) {
                sbuilder.append(str);
                str = input.readLine();
                if (str != null) {
                    sbuilder.append("\n");
                }
            }

        }
        finally {
            input.close();
            fStream.close();
        }

        String s = sbuilder.toString();

    }

    @Test
    public void stringWriter(String fileName) throws IOException {

        char[] buff = new char[1024];
        Writer stringWriter = new StringWriter();
        FileInputStream fStream = null;

        try {

            fStream = new FileInputStream(fileName);
            Reader bReader = new BufferedReader(new InputStreamReader(fStream, "UTF-8"));
            int n;
            while ((n = bReader.read(buff)) != -1) {
                stringWriter.write(buff, 0, n);
            }
        }
        finally {
            stringWriter.close();
            fStream.close();
        }
        String s = stringWriter.toString();
    }

    @Test
    public void scanner(String fileName) throws IOException {

        FileInputStream fStream = null;
        Scanner scanner = null;

        try {
            fStream = new FileInputStream(fileName);
            scanner = new Scanner(fStream, "UTF-8");
            String s = scanner.useDelimiter("\\A").next();
        }
        finally {
            fStream.close();
            scanner.close();
        }

    }

    private String asString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
