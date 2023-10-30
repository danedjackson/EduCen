package com.jackson.educen.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class ScoreDTO {
    private String studentId;
    private String grade;
    private String subject;
    private String assignmentId;
    private String score;
    private LocalDate dateRecorded;
    private String teacherId;
    private String comment;
}
