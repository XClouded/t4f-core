package io.aos.aop.util.retry.service;

import io.aos.aop.util.retry.annotation.RetryOnException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectExcepting implements Connect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectExcepting.class);

    @RetryOnException(
        maxRetry = 5,
        sleepMillis = 5000,
        message = "Can not connect...")
    public void connect() throws Exception {

        LOGGER.info("Trying to connect...");

        Exception e = new Exception("Exception while connecting...");
        LOGGER.error("Damned, got an exception...");
        throw e;

    }

}
