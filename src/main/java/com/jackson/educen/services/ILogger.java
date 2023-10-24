package com.jackson.educen.services;

public interface ILogger {
    void infoLog(String message);
    void debugLog(String message);
    void warnLog(String message);
    void errorLog(String message);
}
