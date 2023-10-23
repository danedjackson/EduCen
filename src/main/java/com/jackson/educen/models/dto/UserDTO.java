package com.jackson.educen.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String zipCode;
    private String contactNumber;
    private String email;
    private int type;
    private String access;
}
