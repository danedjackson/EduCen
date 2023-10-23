package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.dto.ScoreDTO;
import org.springframework.stereotype.Component;

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

        return scoreDocument;
    }
}
