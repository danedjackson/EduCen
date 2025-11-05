package com.jackson.educen.utils;

import com.jackson.educen.models.ApiResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;

public final class Util {

    private Util() {}

    public static int getAge(LocalDate now, LocalDate dob) {
        if (dob.isAfter(now)) {
            return 0;
        }
        Period period = Period.between(dob, now);
        return period.getYears();
    }

    // Return success response
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK, data, message);
    }

    // Return failure response
    public static <T> ApiResponse<T> failure(HttpStatus status, String message) {
        return new ApiResponse<>(status, null, message);
    }
}
