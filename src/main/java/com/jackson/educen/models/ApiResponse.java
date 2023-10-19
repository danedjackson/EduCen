package com.jackson.educen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    private HttpStatus httpStatus;
    private T response;
    private String message;
    private LocalDateTime timeStamp;

    public ApiResponse(HttpStatus httpStatus, T response, String message) {
        this.httpStatus = httpStatus;
        this.response = response;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
