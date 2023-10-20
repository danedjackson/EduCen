package com.jackson.educen.mappers;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.models.Score;
import com.jackson.educen.models.ScoreRequest;

public interface IScoreMapper {
    Score scoreDocumentToScore(ScoreDocument Score);
    ScoreDocument scoreRequestToScoreDocument(ScoreRequest scoreRequest);
}
