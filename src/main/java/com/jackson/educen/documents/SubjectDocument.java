package com.jackson.educen.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("subjects")
@NoArgsConstructor
public class SubjectDocument {
    @Id
    private String id;
    private List<String> subjects;
}
