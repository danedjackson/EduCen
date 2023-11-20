package com.jackson.educen.models.dto.SignIn;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
//    private String username;
    private String password;
}
