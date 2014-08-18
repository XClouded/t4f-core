package io.aos.endpoint.socket.nio;

import java.util.concurrent.Future;

public class NioFutureFetcherExample {
    public interface Fetcher {
        Future<Data> fetchData();
    }

    public class Worker {
        public void doWork() {
            Fetcher fetcher = null; // obtainputStreamreference to fetcher implementation
            Future<Data> future = fetcher.fetchData();
            try {
                while(!future.isDone()) {

                    // do something else
                }
                System.out.println("Data received: " + future.get());

            } catch (Throwable cause) {
                System.err.println("An error accour: " + cause.getMessage());

            }
        }
    }

    public class Data {
        // holds your data
    }
}
