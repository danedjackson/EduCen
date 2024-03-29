package com.jackson.educen.controllers;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.RefreshToken.RefreshTokenRequest;
import com.jackson.educen.models.dto.SignIn.SignInRequest;
import com.jackson.educen.models.dto.SignIn.SignInResponse;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.services.IAuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ApiResponse<User> signup(@RequestBody UserDTO signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public ApiResponse<SignInResponse> signin(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }
    @PostMapping("/refresh")
    public ApiResponse<SignInResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.resfreshToken(refreshTokenRequest);
    }
}
