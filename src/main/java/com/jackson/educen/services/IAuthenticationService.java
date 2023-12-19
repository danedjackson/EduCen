package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.models.dto.RefreshToken.RefreshTokenRequest;
import com.jackson.educen.models.dto.SignIn.SignInRequest;
import com.jackson.educen.models.dto.SignIn.SignInResponse;
import com.jackson.educen.models.dto.UserDTO;

public interface IAuthenticationService {
    public ApiResponse<User> signUp(UserDTO signUpRequest);
    public ApiResponse<SignInResponse> signIn(SignInRequest signInRequest);
    public ApiResponse<SignInResponse> resfreshToken(RefreshTokenRequest refreshTokenRequest);
}
