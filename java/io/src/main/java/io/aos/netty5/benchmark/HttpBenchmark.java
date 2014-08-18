package io.aos.netty5.benchmark;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;

public class HttpBenchmark {
    static int threadCount = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("BenchmarkHttp url content[thread-count]");
            System.out.println("e.g. BenchmarkHttp http://www.baidu.com {\"name\":\"xuhuiqing\"}");
            return;
        }

        System.out.println(args[0] + "," + args[1] + "," + threadCount);
        System.out.println();
        System.out.println("ID    CURRENT CURRENT-LAT/ms AVERAGE MIN   MAX   TOTAL-TIME/ms TOTAL-COUNT TOTAL-LAT/ms");
        ExecutorService pool = Executors.newCachedThreadPool();

        if (args.length > 2) {
            threadCount = Integer.parseInt(args[2]);
        }

        Thread statisticsThread = new Thread(Statistics.getInstance());
        statisticsThread.setDaemon(true);
        statisticsThread.start();

        for (int i = 0; i < threadCount; i++) {
            pool.execute(new Post(args[0], args[1]));
        }

        pool.shutdown();
    }

    private static class Statistics extends Thread {
        private static Statistics instance = new Statistics();
        private AtomicLong sum = new AtomicLong(0);
        private AtomicLong count = new AtomicLong(0);

        private Statistics() {
        }

        public static Statistics getInstance() {
            return instance;
        }

        public void update(long time) {
            sum.addAndGet(time);
            count.incrementAndGet();
        }

        public void run() {
            Thread.currentThread().setName("statistics thread");

            long max_num = -1, precount = 0, current = 0, index = 1, sum, count, avg, min_num = Long.MAX_VALUE;
            double totalLatency, currentLatency;

            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sum = this.sum.get();
                count = this.count.get();
                current = count - precount;
                max_num = current > max_num ? current : max_num;
                min_num = current < min_num ? current : min_num;
                avg = count / index;
                totalLatency = count != 0 ? sum * 1.0 / count : -1.0D;
                currentLatency = current != 0 ? threadCount * 1000.0 / current : -1.0D;

                System.out.println(String.format(
                        "%-5d %-7d %-14.3f %-7d %-5d %-5d %-13d %-11d %-12.3f", (index++),
                        current, currentLatency, avg, min_num,
                        max_num, sum, count, totalLatency));
                precount = count;
            }
        }
    }

    private static class Post extends Thread {
        private final String url;
        private final String content;

        public Post(String url, String content) {
            this.url = url;
            this.content = content;
        }

        public void run() {
            Thread.currentThread().setName("POST-" + url);
            long time1, time2;
            byte[] buff = new byte[10240];
            final byte[] source = content.getBytes();
            
            while (true) {
                try {
                    time1 = System.currentTimeMillis();
                    URL httpUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.setDoOutput(true);
                    connection.connect();
                    final OutputStream os = connection.getOutputStream();
                    os.write(source);

                    boolean isGzip = "gzip".equalsIgnoreCase(connection.getHeaderField("Content-Encoding"));
                    final InputStream is = isGzip ?
                            new GZIPInputStream(connection.getInputStream()) : connection.getInputStream();

                    while (true) {
                        int cnt = is.read(buff);
                        if (cnt == -1) {
                            break;
                        }
//                        System.out.println(new String(buff, 0, cnt));
                    }
                    time2 = System.currentTimeMillis();
                    Statistics.getInstance().update(time2 - time1);
                } catch (Exception e) {
                    System.out.println("error, " + e);
                    break;
                }
            }

        }

        public static String getRandomString() {
            return "" + (char) (Math.random() * 26 + 'A')
                    + (char) (Math.random() * 26 + 'A')
                    + (char) (Math.random() * 26 + 'A')
                    + (char) (Math.random() * 26 + 'A')
                    + (char) (Math.random() * 26 + 'A')
                    + (char) (Math.random() * 26 + 'A');
        }
    }

}
