package com.jackson.educen.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Document("files")
public class FileDocument {
    @Id
    private String id;
    @Field("teacher_id")
    private String teacherId;
    @Field("title")
    private String title;
    @Field("comments")
    private String comments;
    @Field("date_uploaded")
    private LocalDate dateUploaded;
    @Field("document")
    private Binary document;
    @Field("checked")
    private Boolean checked = false;
}
