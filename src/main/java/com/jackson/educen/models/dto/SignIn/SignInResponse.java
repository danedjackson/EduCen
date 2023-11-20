package com.jackson.educen.models.dto.SignIn;

import lombok.Data;

@Data
public class SignInResponse {
    private String token;
    private String refreshToken;
}
