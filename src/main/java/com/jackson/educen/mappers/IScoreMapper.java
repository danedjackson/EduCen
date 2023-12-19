package com.jackson.educen.mappers;

import com.jackson.educen.documents.ScoreDocument;
import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.models.dto.Score;
import com.jackson.educen.models.dto.ScoreDTO;
import com.jackson.educen.models.dto.UserScoreDTO;

import java.util.List;

public interface IScoreMapper {
    Score scoreDocumentToScore(ScoreDocument Score);
    ScoreDocument scoreRequestToScoreDocument(ScoreDTO scoreDTO);
    ScoreDTO scoreDocumentToScoreDTO(ScoreDocument scoreDocument);
    UserScoreDTO scoreUserDocumentsToUserScoreDTO(ScoreDocument scoreDocument, List<UserDocument> userDocument);
}
