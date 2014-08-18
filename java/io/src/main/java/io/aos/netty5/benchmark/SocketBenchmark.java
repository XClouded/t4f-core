package io.aos.netty5.benchmark;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * actual cmd: java BenchmarkSocket 192.168.0.22 1024 1024 50
 */
public class SocketBenchmark {
    static int threadCount = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public static void main(String[] args) throws Exception {
        args = new String[]{"localhost", "1024", "1024"};
//        if (args.length < 3) {
//            System.out.println("BenchmarkSocket host port len [thread-count]");
//            System.out.println("e.g. BenchmarkSocket 192.168.0.22 1024 1024");
//            return;
//        }

        if (args.length > 3) {
            threadCount = Integer.parseInt(args[3]);
        }

        System.out.println(args[0] + "," + args[1] + "," + args[2] + ", " + threadCount);
        System.out.println();
        System.out.println("ID    CURRENT CURRENT-LAT/ms AVERAGE MIN   MAX   TOTAL-TIME/ms TOTAL-COUNT TOTAL-LAT/ms");
        ExecutorService pool = Executors.newCachedThreadPool();


        Thread statisticsThread = new Thread(Statistics.getInstance());
        statisticsThread.setDaemon(true);
        statisticsThread.start();

        final InetSocketAddress server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        final byte[] buff = new byte[Integer.parseInt(args[2])];
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new Post(server, buff));
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
        private final InetSocketAddress server;
        private final byte[] content;

        public Post(InetSocketAddress server, byte[] content) {
            this.server = server;
            this.content = content;
        }

        public void run() {
            long time1, time2;
            byte[] buff = new byte[10240];
            Socket socket = new Socket();
            try {
                socket.connect(server);
                final OutputStream os = socket.getOutputStream();
                final InputStream is = socket.getInputStream();

                while (true) {
                    time1 = System.currentTimeMillis();
                    os.write(content);

                    int len = content.length;
                    while (len > 0) {
                        int cnt = is.read(buff);
                        if (cnt == -1) {
                            break;
                        }

                        len -= cnt;
                    }

                    time2 = System.currentTimeMillis();
                    Statistics.getInstance().update(time2 - time1);
                }
            } catch (Exception e) {
                System.out.println("error, " + e);
            }
        }
    }

}
