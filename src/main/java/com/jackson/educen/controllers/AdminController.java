package com.jackson.educen.controllers;


import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.services.ITeacherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ITeacherService teacherService;

    public AdminController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/add-teacher")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // ADMINS (Administrators) create new USER(Teacher) documents
        return null;

    }

    @PostMapping("/update-plan/{id}")
    public ApiResponse<String> updateFile(@RequestBody FileDocument fileInfo) {
        return teacherService.updateFile(fileInfo);
    }

}
