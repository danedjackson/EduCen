package com.jackson.educen.controllers;

import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ISubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/subjects")
public class SubjectController {
    private final ISubjectService subjectService;
    private final ILogger logger;

    public SubjectController(ISubjectService subjectService, ILogger logger) {
        this.subjectService = subjectService;
        this.logger = logger;
    }

    @GetMapping("/all")
    public ApiResponse<List<String>> getAllSubjects() {
        logger.infoLog("Initiating request to get all subjects");
        return subjectService.getAllSubjects();
    }
}
