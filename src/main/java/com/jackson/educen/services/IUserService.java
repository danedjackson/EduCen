package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;

public interface IUserService {
    public ApiResponse<User> getUserById(String id);
    public ApiResponse<User> addNewUser(User user);
    public ApiResponse<User> editUserDetails(User user);
}
