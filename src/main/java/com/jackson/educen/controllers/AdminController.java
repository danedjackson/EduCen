package com.jackson.educen.controllers;


import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.services.ITeacherService;
import com.jackson.educen.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final IUserService userService;
    private final ITeacherService teacherService;

    public AdminController(IUserService userService, ITeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
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

    @PostMapping("/update-plan/{id}")
    public ApiResponse<String> updateFile(@RequestBody FileDocument fileInfo) {
        return teacherService.updateFile(fileInfo);
    }

}
