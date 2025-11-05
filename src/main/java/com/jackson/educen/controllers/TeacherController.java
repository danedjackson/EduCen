package com.jackson.educen.controllers;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.FileDownload;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ITeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@CrossOrigin
public class TeacherController {

    private final ITeacherService teacherService;
    private final ILogger logger;

    public TeacherController(ITeacherService teacherService, ILogger logger) {
        this.teacherService = teacherService;
        this.logger = logger;
    }
    @PostMapping("/upload/{subject}")
    public ApiResponse<FileDocument> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String subject) {
        return teacherService.uploadFile(file, subject);
    }

    @GetMapping("/plan/{planId}")
    public ApiResponse<FileDownload> getFile(@PathVariable String planId) {
        return teacherService.getLessonPlan(planId);
    }

    @GetMapping("/plans/{planId}")
    public ApiResponse<List<FileDocument>> getLessonPlans(@PathVariable String planId) {
        return teacherService.getLessonPlans(planId);
    }

    @GetMapping("/plans/get-teacher-plans/{teacherId}")
    public ApiResponse<List<FileDocument>> getTeacherLessonPlans(@PathVariable String teacherId) {
        return teacherService.getTeacherLessonPlans(teacherId);
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

    @PostMapping("/create-student")
    public ApiResponse<User> addUser(@RequestBody UserDTO user) {
        // USERS (Teachers) create new student documents
        user.setRole(Role.STUDENT);
        return teacherService.addNewStudent(user);
    }

    @PutMapping("/update-student")
    public ApiResponse<User> editUser(@RequestBody UserDTO user) {
        user.setRole(Role.STUDENT);
        return teacherService.editStudentDetails(user);
    }
}
