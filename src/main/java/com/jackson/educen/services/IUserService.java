package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.models.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService {
    public ApiResponse<User> getUserById(String id);
    public ApiResponse<List<User>> getAllUsersOfType(int typeId);
    public ApiResponse<User> addNewUser(UserDTO user);
    public ApiResponse<User> editUserDetails(UserDTO user);
    public UserDetailsService userDetailsService();
}
