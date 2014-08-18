package io.aos.aop.util.retry.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryExecutor.class);

    public Object executeWithRetry(int maxRetryCount, String errorMessage, int sleepMillis, Callable callable)
            throws Throwable {

        Throwable cause = null;

        int remainingRetryCount = maxRetryCount;

        while (remainingRetryCount-- > 0) {
            try {
                Object result = callable.call();
                LOGGER.info("Success after " + (maxRetryCount - remainingRetryCount) + " retries.");
                return result;
            }
            catch (Throwable t) {
                cause = t;
                LOGGER.warn("Got an exception at attempt[" + (maxRetryCount - remainingRetryCount) + "] with message["
                        + errorMessage + "]");
                LOGGER.warn("Sleeping now for " + sleepMillis + " millis");
                sleep(sleepMillis);
                LOGGER.warn("Now retrying with remainingRetryCount[" + remainingRetryCount + "]");
                if (callable instanceof RecoveringCallable) {
                    try {
                        LOGGER.warn("Now recovering");
                        ((RecoveringCallable) callable).recover();
                    }
                    catch (Throwable t2) {
                        LOGGER.warn("Recovery action failed.");
                        throw t2;
                    }
                }
            }
        }

        LOGGER.error("Maximum retries count[" + maxRetryCount + "] exceeded. Rethrowing the cause.", cause);

        throw cause;

    }

    private static void sleep(int sleepMillis) {
        try {
            Thread.sleep(sleepMillis);
        }
        catch (InterruptedException e) {
            LOGGER.error("Interrupted", e);
            throw new RuntimeException(e);
        }
    }

    public interface Callable {
        Object call() throws Throwable;
    }

    public interface RecoveringCallable extends Callable {
        void recover() throws Throwable;
    }

}
