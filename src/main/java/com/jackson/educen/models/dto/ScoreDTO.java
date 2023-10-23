package com.jackson.educen.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class ScoreDTO {
    @JsonProperty("student_id")
    private String studentId;
    private String grade;
    private String subject;
    @JsonProperty("assignment_id")
    private String assignmentId;
    private String score;
    @JsonProperty("date_recorded")
    private LocalDate dateRecorded;
    @JsonProperty("teacher_id")
    private String teacherId;
}
