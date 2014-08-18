package io.aos.concurrent.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ScheduledTest {

    @Test
    public void test() {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture cacheCleaningTask = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Do something...
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

}
