package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.mappers.IScoreMapper;
import com.jackson.educen.models.Score;
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
}
