package com.jackson.educen.models.dto.Score;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Score {
    private String scoreId;
    private String grade;
    private String subject;
    private String assignmentId;
    private String score;
    private LocalDate dateRecorded;
    private String comment;
}
