package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.SignIn.SignInRequest;
import com.jackson.educen.models.dto.SignIn.SignInResponse;
import com.jackson.educen.models.dto.UserDTO;

public interface IAuthenticationService {
    public ApiResponse<User> signUp(UserDTO signUpRequest);
    public ApiResponse<SignInResponse> signIn(SignInRequest signInRequest);
}
