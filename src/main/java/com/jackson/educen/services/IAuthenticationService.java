package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;

public interface IAuthenticationService {
    public default ApiResponse<User> signup(UserDTO signUpRequest) {
        return null;
    }
}
