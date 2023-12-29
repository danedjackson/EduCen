package com.jackson.educen.controllers;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ITeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeacherController {

    private final ITeacherService teacherService;
    private final ILogger logger;

    public TeacherController(ITeacherService teacherService, ILogger logger) {
        this.teacherService = teacherService;
        this.logger = logger;
    }
    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return teacherService.uploadFile(file);
    }

    @GetMapping("/plans/{id}")
    public ApiResponse<FileDocument> getFile(@PathVariable String id) {
        return teacherService.getFile(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserInformation(@PathVariable String id) {
        logger.infoLog("Starting request to fetch user information given ID: " + id);
        return teacherService.getStudentById(id);
    }

    @GetMapping("/all-students")
    public ApiResponse<List<User>> getAllStudents() {
        return teacherService.getAllStudents();
    }

    @PostMapping("/add")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // USERS (Teachers) create new student documents
        user.setRole(Role.STUDENT);
        return teacherService.addNewStudent(user);
    }

    @PatchMapping("/edit")
    public ApiResponse<User> editUser(@RequestBody UserDTO user) {
        return teacherService.editStudentDetails(user);
    }
}
