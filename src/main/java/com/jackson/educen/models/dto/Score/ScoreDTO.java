package com.jackson.educen.models.dto.Score;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class    ScoreDTO {
    //TODO: Replace Score model with this one
    private String scoreId;
    private String studentId;
    private String grade;
    private String subject;
    private String assignmentId;
    private String score;
    private LocalDate dateRecorded;
    private String teacherId;
    private String comment;
}
