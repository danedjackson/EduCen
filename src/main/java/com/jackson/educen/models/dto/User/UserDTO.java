package com.jackson.educen.models.dto.User;

import com.jackson.educen.models.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String zipCode;
    private String contactNumber;
    private String email;
    private String grade;
    private Role role;
}
