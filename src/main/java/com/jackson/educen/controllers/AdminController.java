package com.jackson.educen.controllers;


import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.services.ITeacherService;
import com.jackson.educen.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ITeacherService teacherService;
    private final IUserService userService;

    public AdminController(ITeacherService teacherService, IUserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @PostMapping("/add-teacher")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // ADMINS (Administrators) create new USER(Teacher) documents
        return null;
    }

    @GetMapping("/teachers")
    public ApiResponse<List<User>> getAllTeachers(){
        return userService.getAllUsersByRole(Role.TEACHER.name());
    }


    @PostMapping("/update-plan/{id}")
    public ApiResponse<String> updateFile(@RequestBody FileDocument fileInfo) {
        return teacherService.updateFile(fileInfo);
    }

}
