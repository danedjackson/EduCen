package com.jackson.educen.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int age;
    private String address;
    private String city;
    private String zipCode;
    private String contactNumber;
    private String email;
    private String grade;
    private String access;
}
