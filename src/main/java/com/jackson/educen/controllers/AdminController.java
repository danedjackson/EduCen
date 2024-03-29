package com.jackson.educen.controllers;


import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.models.requests.UpdatePlanRequest;
import com.jackson.educen.services.ITeacherService;
import com.jackson.educen.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        user.setRole(Role.TEACHER);
        return userService.addNewUser(user);
    }

    @GetMapping("/teachers")
    public ApiResponse<List<User>> getAllTeachers(){
        return userService.getAllUsersByRole(Role.TEACHER.name());
    }


    @PostMapping("/update-plan/{id}")
    public ApiResponse<String> updateFile(@RequestBody FileDocument fileInfo) {
        return teacherService.updateFile(fileInfo);
    }

    @PostMapping("/update-plans")
    public ApiResponse<List<FileDocument>> updatePlans(@RequestBody Map<String, String> request) {
        UpdatePlanRequest planComments = new UpdatePlanRequest();
        planComments.setPlanComments(request);
        return teacherService.updateFileComments(planComments);
    }

    @GetMapping("/plans")
    public ApiResponse<List<UserFile>> getAllPlans() {
        return teacherService.getAllTeacherPlans(   );
    }
}
