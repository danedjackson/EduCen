package com.jackson.educen.mappers;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.models.Score;

public interface IScoreMapper {
    Score scoreDocumentToScore(ScoreDocument Score);
}
