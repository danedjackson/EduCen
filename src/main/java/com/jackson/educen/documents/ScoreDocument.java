package com.jackson.educen.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Document("scores")
@NoArgsConstructor
public class ScoreDocument {
    @Id
    private String id;
    @Field("student_id")
    private String studentId;
    @Field("grade")
    private String grade;
    @Field("subject")
    private String subject;
    @Field("assignment_id")
    private String assignmentId;
    @Field("score")
    private String score;
    @Field("date_recorded")
    private LocalDate dateRecorded;
    @Field("teacher_id")
    private String teacherId;
}
