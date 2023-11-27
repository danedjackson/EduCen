package com.jackson.educen.controllers;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final IUserService userService;
    private final ILogger logger;

    public UserController(IUserService userService, ILogger logger) {
        this.userService = userService;
        this.logger = logger;
    }
    @GetMapping("/{id}")
    public ApiResponse<User> getUserInformation(@PathVariable String id) {
        logger.infoLog("Starting request to fetch user information given ID: " + id);
        return userService.getUserById(id);
    }

    @GetMapping("/type/{typeId}")
    public ApiResponse<List<User>> getAllUsersOfType(@PathVariable int typeId) {
        return userService.getAllUsersOfType(typeId);
    }

    @PostMapping("/add")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // USERS (Teachers) create new student documents
        user.setRole(Role.STUDENT);
        return userService.addNewUser(user);
    }

    @PatchMapping("/edit")
    public ApiResponse<User> editUser(@RequestBody UserDTO user) {
        return userService.editUserDetails(user);
    }
}
