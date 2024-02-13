package com.jackson.educen.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class File {
    private String id;
    private String title;
    private String teacherId;
    private String comments;
    //private String document;
}
