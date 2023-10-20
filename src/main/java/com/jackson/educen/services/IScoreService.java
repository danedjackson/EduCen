package com.jackson.educen.services;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.ScoreRequest;

import java.util.List;

public interface IScoreService {
    ApiResponse<List<Score>> getAllGradeSubjectScores(String id, String grade, String subject);
    ApiResponse<List<Score>> getAllGradeScores(String id, String grade);
    ApiResponse<List<Score>> getAllScores(String id);
    ApiResponse<Score> addStudentScore(ScoreRequest scoreDocument);
}
