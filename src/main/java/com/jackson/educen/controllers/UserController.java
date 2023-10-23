package com.jackson.educen.controllers;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ApiResponse<User> getUserInformation(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/type/{typeId}")
    public ApiResponse<List<User>> getAllUsersOfType(@PathVariable int typeId) {
        return userService.getAllUsersOfType(typeId);
    }
}
