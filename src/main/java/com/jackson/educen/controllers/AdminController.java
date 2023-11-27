package com.jackson.educen.controllers;


import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final IUserService userService;

    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<String> sayHello(){
        return new ApiResponse<>(HttpStatus.OK, "HI ADMIN", "");
    }


    @PostMapping("/add-teacher")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // ADMINS (Administrators) create new USER(Teacher) documents
        user.setRole(Role.USER);
        return userService.addNewUser(user);
    }
}
