package com.jackson.educen.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Field("date_modified")
    private LocalDateTime dateModified;

    @Field("checked")
    private Boolean checked = false;

    @Field("content_type")
    private String contentType;

    @Field("file_path")
    private String filePath;

    @Field("subject")
    private String subject;
}
