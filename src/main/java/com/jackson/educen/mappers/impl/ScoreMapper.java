package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.ScoreRequest;
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
    public ScoreDocument scoreRequestToScoreDocument(ScoreRequest scoreRequest) {
        if(null==scoreRequest.getStudentId()) {
            return new ScoreDocument();
        }
        ScoreDocument scoreDocument = new ScoreDocument();

        scoreDocument.setScore(scoreRequest.getScore());
        scoreDocument.setGrade(scoreRequest.getGrade());
        scoreDocument.setSubject(scoreRequest.getSubject());
        scoreDocument.setAssignmentId(scoreRequest.getAssignmentId());
        scoreDocument.setStudentId(scoreRequest.getStudentId());
        scoreDocument.setTeacherId(scoreRequest.getTeacherId());
        scoreDocument.setDateRecorded(scoreRequest.getDateRecorded());

        return scoreDocument;
    }
}
