package com.jackson.educen.utils;

import java.time.LocalDate;
import java.time.Period;

public class Util {
    public int getAge(LocalDate now, LocalDate dob) {
        if (dob.isAfter(now)) {
            return 0;
        }
        Period period = Period.between(dob, now);
        return period.getYears();
    }
}
