package com.jackson.educen.models.dto.SignIn;

import com.jackson.educen.models.Role;
import lombok.Data;

@Data
public class SignInResponse {
    private String id;
    private String firstName;
    private Role role;
    private String grade;
    private String token;
    private String refreshToken;
}
