package com.jackson.educen.controllers;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.ScoreRequest;
import com.jackson.educen.services.IScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/score")
@CrossOrigin
public class ScoreController {
    private final IScoreService scoreService;

    public ScoreController(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/{studentId}")
    public ApiResponse<List<Score>> getStudentScoresForSubjectAndGrade(@PathVariable String studentId,
                                                                       @RequestParam(name = "subject", required = false) String subject,
                                                                       @RequestParam(name = "grade", required = false) String grade) {
        if(null != subject && null != grade) {
            return scoreService.getAllGradeSubjectScores(studentId, grade, subject);
        }
        else if(null != grade) {
            return scoreService.getAllGradeScores(studentId, grade);
        }
        else {
            return scoreService.getAllScores(studentId);
        }
    }

    @PostMapping
    public ApiResponse<Score> addStudentScore(@RequestBody ScoreRequest scoreRequest) {
        return scoreService.addStudentScore(scoreRequest);
    }
}
