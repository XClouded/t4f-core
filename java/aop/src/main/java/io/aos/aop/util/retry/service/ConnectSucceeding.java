package io.aos.aop.util.retry.service;

import io.aos.aop.util.retry.annotation.RetryOnException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectSucceeding implements Connect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectSucceeding.class);

    @RetryOnException(
        maxRetry = 3,
        sleepMillis = 5000,
        message = "Can not connect...")
    public void connect() throws Exception {

        LOGGER.info("Connecting successfully...");

    }

}
