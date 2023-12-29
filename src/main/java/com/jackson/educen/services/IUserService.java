package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService {
    public UserDetailsService userDetailsService();
    public ApiResponse<User> getUserById(String id);
    public ApiResponse<List<User>> getAllUsersByRole(String role);
    public ApiResponse<User> addNewUser(UserDTO user);
    public ApiResponse<User> editUserDetails(UserDTO user);
}
