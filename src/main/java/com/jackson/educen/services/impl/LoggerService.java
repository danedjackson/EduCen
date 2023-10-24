package com.jackson.educen.services.impl;
import org.slf4j.Logger;

import com.jackson.educen.services.ILogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService implements ILogger {
    private final Logger logger;

    public LoggerService() {
        this.logger = LoggerFactory.getLogger(LoggerService.class);
    }

    @Override
    public void infoLog(String message) {
        logger.info(message);
    }

    @Override
    public void debugLog(String message) {
        logger.debug(message);
    }

    @Override
    public void warnLog(String message) {
        logger.warn(message);
    }

    @Override
    public void errorLog(String message) {
        logger.error(message);
    }
}
