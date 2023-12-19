package com.jackson.educen.controllers;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.services.ITeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeacherController {

    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return teacherService.uploadFile(file);
    }

    @GetMapping("/plans/{id}")
    public ApiResponse<FileDocument> getPhoto(@PathVariable String id) {
        return teacherService.getFile(id);
    }
}
