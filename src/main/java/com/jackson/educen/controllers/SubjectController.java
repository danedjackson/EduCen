package com.jackson.educen.controllers;

import com.jackson.educen.documents.SubjectDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.Score.UserScoreDTO;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ISubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subjects")
@CrossOrigin
public class SubjectController {
    private final ISubjectService subjectService;
    private final ILogger logger;

    public SubjectController(ISubjectService subjectService, ILogger logger) {
        this.subjectService = subjectService;
        this.logger = logger;
    }

    @GetMapping("/all")
    public ApiResponse<List<SubjectDocument>> getAllSubjects() {
        logger.infoLog("Initiating request to get all subjects");
        return subjectService.getAllSubjects();
    }

    @PostMapping("/add/{subjectName}")
    public ApiResponse<SubjectDocument> addSubject(@PathVariable String subjectName) {
        logger.infoLog("Initiating request to add subject ["+subjectName+"]");
        return subjectService.saveSubject(subjectName);
    }

    @PostMapping("/remove/{subjectName}")
    public ApiResponse<SubjectDocument> removeSubject(@PathVariable String subjectName) {
        logger.infoLog("Initiating request to remove subject ["+subjectName+"]");
        return subjectService.removeSubject(subjectName);
    }

    @PutMapping("/edit/{oldSubjectName}/{newSubjectName}")
    public ApiResponse<SubjectDocument> editSubject(@PathVariable String oldSubjectName, @PathVariable String newSubjectName) {
        logger.infoLog("Initiating request to edit subject from [" + oldSubjectName + "] to [" + newSubjectName + "]");
        return subjectService.editSubject(oldSubjectName, newSubjectName);
    }
}
