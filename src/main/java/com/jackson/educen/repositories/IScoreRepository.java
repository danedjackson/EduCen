package com.jackson.educen.repositories;

import com.jackson.educen.documents.ScoreDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IScoreRepository extends MongoRepository<ScoreDocument, String> {
    @Query("{'student_id': ?0, 'grade': ?1, 'subject': ?2}")
    List<ScoreDocument> findAllScoresGivenGradeSubject(String studentId, String grade, String subject);
    @Query("{'student_id': ?0, 'grade': ?1}")
    List<ScoreDocument> findAllScoresGivenGrade(String studentId, String grade);
    @Query("{'student_id': ?0}")
    List<ScoreDocument> findAllScores(String studentId);
}
