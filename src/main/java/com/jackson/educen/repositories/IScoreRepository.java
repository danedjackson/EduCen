package com.jackson.educen.repositories;

import com.jackson.educen.documents.ScoreDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IScoreRepository extends MongoRepository<ScoreDocument, String> {
    @Query("{'student_id': ?0, 'grade': ?1, 'subject': ?2}")
    List<ScoreDocument> findAllScoresGivenIdGradeSubject(String studentId, String grade, String subject);
    @Query("{'student_id': ?0, 'grade': ?1}")
    List<ScoreDocument> findAllScoresGivenIdGrade(String studentId, String grade);
    @Query("{'student_id': ?0}")
    List<ScoreDocument> findAllStudentScores(String studentId);
    @Query("{'grade': ?0}")
    List<ScoreDocument> findAllScoresGivenGrade(String grade);
    @Query("{'_id': ?0}")
    Optional<ScoreDocument> findScoreById(ObjectId id);
    @Query(value="{'_id': ?0}", delete = true)
    public void deleteScoreById(String id);
}
