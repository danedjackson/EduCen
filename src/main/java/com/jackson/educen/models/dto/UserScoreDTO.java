package com.jackson.educen.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserScoreDTO {
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String grade;
    private String subject;
    private String assignmentId;
    private String score;
    private LocalDate dateRecorded;
    private String teacherId;
}
