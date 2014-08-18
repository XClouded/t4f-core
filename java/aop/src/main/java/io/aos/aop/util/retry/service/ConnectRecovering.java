package io.aos.aop.util.retry.service;

import io.aos.aop.util.retry.annotation.RetryOnException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectRecovering implements Connect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectRecovering.class);

    private int tryCount = 0;

    @RetryOnException(
        maxRetry = 5,
        sleepMillis = 5000,
        message = "Can not connect...")
    public void connect() throws Exception {

        LOGGER.warn("Trying to connect...");

        tryCount++;

        if (tryCount < 3) {
            Exception e = new Exception("Exception while connecting...");
            LOGGER.error("Damned, got an exception...");
            throw e;
        }

        LOGGER.info("Connect is finally successfull after 3 retries.");

    }

}
