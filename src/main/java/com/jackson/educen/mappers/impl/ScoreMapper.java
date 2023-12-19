package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.dto.Score;
import com.jackson.educen.models.dto.ScoreDTO;
import com.jackson.educen.models.dto.UserScoreDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ScoreMapper implements IScoreMapper {
    @Override
    public Score scoreDocumentToScore(ScoreDocument scoreDocument) {
        if(null == scoreDocument.getId()) {
            return new Score();
        }
        Score score = new Score();
        score.setSubject(scoreDocument.getSubject());
        score.setGrade(scoreDocument.getGrade());
        score.setScore(scoreDocument.getScore());
        score.setDateRecorded(scoreDocument.getDateRecorded());
        score.setAssignmentId(scoreDocument.getAssignmentId());
        score.setComment(scoreDocument.getComment());
        score.setScoreId(scoreDocument.getId());

        return score;
    }

    @Override
    public ScoreDocument scoreRequestToScoreDocument(ScoreDTO scoreDTO) {
        if(null== scoreDTO.getStudentId()) {
            return new ScoreDocument();
        }
        ScoreDocument scoreDocument = new ScoreDocument();

        scoreDocument.setScore(scoreDTO.getScore());
        scoreDocument.setGrade(scoreDTO.getGrade());
        scoreDocument.setSubject(scoreDTO.getSubject());
        scoreDocument.setAssignmentId(scoreDTO.getAssignmentId());
        scoreDocument.setStudentId(scoreDTO.getStudentId());
        scoreDocument.setTeacherId(scoreDTO.getTeacherId());
        scoreDocument.setDateRecorded(scoreDTO.getDateRecorded());
        scoreDocument.setComment(scoreDTO.getComment());
        scoreDocument.setId(scoreDTO.getScoreId());

        return scoreDocument;
    }

    @Override
    public ScoreDTO scoreDocumentToScoreDTO(ScoreDocument scoreDocument) {
        if(null == scoreDocument.getId()) {
            return new ScoreDTO();
        }
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setScore(scoreDocument.getScore());
        scoreDTO.setScoreId(scoreDocument.getId());
        scoreDTO.setGrade(scoreDocument.getGrade());
        scoreDTO.setComment(scoreDocument.getComment());
        scoreDTO.setSubject(scoreDocument.getSubject());
        scoreDTO.setStudentId(scoreDocument.getStudentId());
        scoreDTO.setAssignmentId(scoreDocument.getAssignmentId());
        scoreDTO.setTeacherId(scoreDocument.getTeacherId());
        scoreDTO.setDateRecorded(scoreDocument.getDateRecorded());

        return scoreDTO;
    }


    @Override
    public UserScoreDTO scoreUserDocumentsToUserScoreDTO(ScoreDocument scoreDocument, List<UserDocument> userDocuments) {
        if(null == scoreDocument.getId() || userDocuments.isEmpty()) {
            return new UserScoreDTO();
        }

        UserScoreDTO userScoreDTO = new UserScoreDTO();
        userScoreDTO.setStudentId(scoreDocument.getStudentId());
        userDocuments.forEach(userDocument -> {
            if(Objects.equals(userDocument.getId(), userScoreDTO.getStudentId())){
                userScoreDTO.setFirstName(userDocument.getFirstName());
                userScoreDTO.setLastName(userDocument.getLastName());
                userScoreDTO.setMiddleName(userDocument.getMiddleName());
            }
        });
        userScoreDTO.setDateRecorded(scoreDocument.getDateRecorded());
        userScoreDTO.setGrade(scoreDocument.getGrade());
        userScoreDTO.setScore(scoreDocument.getScore());
        userScoreDTO.setAssignmentId(scoreDocument.getAssignmentId());
        userScoreDTO.setSubject(scoreDocument.getSubject());
        userScoreDTO.setTeacherId(scoreDocument.getTeacherId());

        return userScoreDTO;
    }
}
