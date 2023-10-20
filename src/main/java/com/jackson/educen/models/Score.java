package com.jackson.educen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class Score {
    private String grade;
    private String subject;
    private String assignmentId;
    private String score;
    private LocalDate dateRecorded;
}
