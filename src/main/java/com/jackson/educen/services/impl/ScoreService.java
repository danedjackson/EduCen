package com.jackson.educen.services.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.Score.Score;
import com.jackson.educen.models.dto.Score.ScoreDTO;
import com.jackson.educen.models.dto.Score.UserScoreDTO;
import com.jackson.educen.repositories.IScoreRepository;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.IScoreService;
import com.jackson.educen.utils.Util;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreService implements IScoreService {
    private final IScoreRepository scoreRepository;
    private final IUserRepository userRepository;
    private final IScoreMapper scoreMapper;
    private final ILogger logger;

    public ScoreService(IScoreRepository scoreRepository, IUserRepository userRepository, IScoreMapper scoreMapper, ILogger logger) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.scoreMapper = scoreMapper;
        this.logger = logger;
    }

    @Override
    public ApiResponse<List<Score>> getAllGradeSubjectScores(String id, String grade, String subject) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllScoresGivenIdGradeSubject(id, grade, subject);
        if(scoreDocuments.isEmpty()) {
            return Util.failure(HttpStatus.NOT_FOUND, "No scores were found for student ID: " + id);
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> scores.add(scoreMapper.scoreDocumentToScore(score)));

        return Util.success(scores, "Scores for student ID: " + id);
    }

    @Override
    public ApiResponse<List<Score>> getAllGradeScores(String id, String grade) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllScoresGivenIdGrade(id, grade);
        if(scoreDocuments.isEmpty()) {
            return Util.failure(HttpStatus.NOT_FOUND, "No scores were found for student ID: " + id);
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> scores.add(scoreMapper.scoreDocumentToScore(score)));

        return Util.success(scores, "Scores for student ID: " + id);
    }

    @Override
    public ApiResponse<List<Score>> getAllScores(String id) {
        List<ScoreDocument> scoreDocuments = scoreRepository.findAllStudentScores(id);
        if(scoreDocuments.isEmpty()) {
            return Util.failure(HttpStatus.NOT_FOUND, "No scores were found for student ID: " + id);
        }
        List<Score> scores = new ArrayList<>();
        scoreDocuments.forEach(score -> scores.add(scoreMapper.scoreDocumentToScore(score)));

        return Util.success(scores, "Scores for student ID: " + id);
    }

    @Override
    public ApiResponse<Score> addStudentScore(ScoreDTO scoreDTO) {
        try {
            if (null == scoreDTO.getStudentId()) {
                logger.errorLog("Could not add record to database. Request is null");
                return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot save score with no data");
            }
            ScoreDocument savedDocument = scoreRepository.save(scoreMapper.scoreRequestToScoreDocument(scoreDTO));
            if (null == savedDocument.getId()) {
                logger.errorLog("Could not add user to database. Saved document ID is null");
                return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong attempting to add student score to the database");
            }
            logger.infoLog("Successfully added user with id '" + savedDocument.getId() + "' to database");
            return Util.success(scoreMapper.scoreDocumentToScore(savedDocument), "Successfully stored student score");
        } catch (Exception e) {
            logger.errorLog("Could not insert student score data: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while adding student score: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<UserScoreDTO>> getAllStudentScores() {
        try {
            List<ScoreDocument> scoreDocuments = scoreRepository.findAll();
            Set<String> studentIds = new HashSet<>();
            scoreDocuments.forEach(scoreDocument -> studentIds.add(scoreDocument.getStudentId()));

            List<UserDocument> userDocuments = userRepository.findAllById(studentIds);

            List<UserScoreDTO> userScore = new ArrayList<>();
            scoreDocuments.forEach(scoreDocument -> userScore.add(scoreMapper.scoreUserDocumentsToUserScoreDTO(scoreDocument, userDocuments)));

            logger.infoLog("Successfully built UserScoreDTO Object and returning");
            return Util.success(userScore, "Successfully fetch User-Score data");
        } catch (Exception e) {
            logger.errorLog("Could not fetch data to populate userScoreDTO: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching User-Score data: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<UserScoreDTO>> getAllStudentScoresByGrade(String grade) {
        try {
            List<ScoreDocument> scoreDocuments = scoreRepository.findAllScoresGivenGrade(grade);
            Set<String> studentIds = new HashSet<>();
            scoreDocuments.forEach(scoreDocument -> studentIds.add(scoreDocument.getStudentId()));

            List<UserDocument> userDocuments = userRepository.findAllById(studentIds);

            List<UserScoreDTO> userScore = new ArrayList<>();
            scoreDocuments.forEach(scoreDocument -> userScore.add(scoreMapper.scoreUserDocumentsToUserScoreDTO(scoreDocument, userDocuments)));

            logger.infoLog("Successfully built UserScoreDTO Object and returning");
            return Util.success(userScore, "Successfully fetch User-Score data");
        } catch (Exception e) {
            logger.errorLog("Could not fetch data to populate userScoreDTO: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching User-Score data: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Boolean> deleteScoreRecord(String scoreId) {
        try {
            if (scoreId.isEmpty()) {
                logger.errorLog("Could not find score ID to be deleted");
                return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "No ID provided for removal");
            }
            logger.infoLog("Deleting score record with ID (" + scoreId +")");
            scoreRepository.deleteScoreById(scoreId);

            if(scoreRepository.findScoreById(new ObjectId(scoreId)).isPresent()) {
                logger.errorLog("Score record was still found after deletion.");
                return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete score record with ID: " + scoreId);
            }
            logger.infoLog("Successfully deleted record with ID: " +scoreId);
            return Util.success(true, "Successfully deleted record with ID: " + scoreId);
        } catch (Exception e) {
            logger.errorLog("Something went wrong attempting to delete score record with ID (" + scoreId + "): " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while deleting score record with ID: " + scoreId);
        }
    }

    public ApiResponse<ScoreDTO> editStudentScore(ScoreDTO scoreDTO) {
        try {
            Optional<ScoreDocument> scoreDocument = scoreRepository.findScoreById(new ObjectId(scoreDTO.getScoreId()));
            if(scoreDocument.isEmpty()) {
                logger.errorLog("Could not find score record with ID: " + scoreDTO.getScoreId());
                return Util.failure(HttpStatus.NOT_FOUND, "Could not find score to edit");
            }
            ScoreDTO fetchedScore = scoreMapper.scoreDocumentToScoreDTO(scoreDocument.get());
            fetchedScore.setDateRecorded(scoreDTO.getDateRecorded());

            if(Objects.equals(scoreDTO, fetchedScore)) {
                logger.warnLog("Both objects are equal. No editing of score was done");
                return Util.failure(HttpStatus.NOT_MODIFIED, "No changes detected.");
            }

            ScoreDocument savedScoreDocument = scoreRepository.save(scoreMapper.scoreRequestToScoreDocument(scoreDTO));
            if(null == savedScoreDocument.getId()) {
                logger.errorLog("Score document was empty after saving");
                return Util.failure(HttpStatus.NOT_MODIFIED, "Could not save edited record.");
            }
            logger.infoLog("Successfully updated score record with ID: " + savedScoreDocument.getId());
            return Util.success(scoreMapper.scoreDocumentToScoreDTO(savedScoreDocument), "Successfully updated score record");
        } catch(Exception e) {
            logger.errorLog("An error occurred updating score: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while editing score: " + e.getMessage());
        }
    }

}
