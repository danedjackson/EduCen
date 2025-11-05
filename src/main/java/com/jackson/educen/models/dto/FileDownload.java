package com.jackson.educen.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDownload {
    private byte[] content;
    private String contentType;
    private String fileName;
}
