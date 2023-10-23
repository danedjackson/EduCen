package com.jackson.educen.services.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.dto.ScoreDTO;
import com.jackson.educen.repositories.IScoreRepository;
import com.jackson.educen.services.IScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService implements IScoreService {
    private final IScoreRepository scoreRepository;
    private final IScoreMapper scoreMapper;

    public ScoreService(IScoreRepository scoreRepository, IScoreMapper scoreMapper) {
        this.scoreRepository = scoreRepository;
        this.scoreMapper = scoreMapper;
    }

    @Override
    public ApiResponse<List<Score>> getAllGradeSubjectScores(String id, String grade, String subject) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllScoresGivenGradeSubject(id, grade, subject);
        if(scoreDocuments.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "No scores were found for student ID: " + id
            );
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> {
            scores.add(scoreMapper.scoreDocumentToScore(score));
        });

        return new ApiResponse<>(
                HttpStatus.OK,
                scores,
                "Scores for student ID: " + id
        );
    }

    @Override
    public ApiResponse<List<Score>> getAllGradeScores(String id, String grade) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllScoresGivenGrade(id, grade);
        if(scoreDocuments.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "No scores were found for student ID: " + id
            );
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> {
            scores.add(scoreMapper.scoreDocumentToScore(score));
        });

        return new ApiResponse<>(
                HttpStatus.OK,
                scores,
                "Scores for student ID: " + id
        );
    }

    @Override
    public ApiResponse<List<Score>> getAllScores(String id) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllScores(id);
        if(scoreDocuments.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "No scores were found for student ID: " + id
            );
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> {
            scores.add(scoreMapper.scoreDocumentToScore(score));
        });

        return new ApiResponse<>(
                HttpStatus.OK,
                scores,
                "Scores for student ID: " + id
        );
    }

    @Override
    public ApiResponse<Score> addStudentScore(ScoreDTO scoreDTO) {
        ScoreDocument savedDocument = scoreRepository.save(scoreMapper.scoreRequestToScoreDocument(scoreDTO));
        if(null == savedDocument.getId()) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Something went wrong attempting to add student score to database"
            );
        }
        return new ApiResponse<>(
                HttpStatus.OK,
                scoreMapper.scoreDocumentToScore(savedDocument),
                "Successfully stored student score"
        );
    }

}
