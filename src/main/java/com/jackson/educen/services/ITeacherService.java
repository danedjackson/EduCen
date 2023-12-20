package com.jackson.educen.services;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface ITeacherService {
    public ApiResponse<String> uploadFile (MultipartFile file);
    public ApiResponse<FileDocument> getFile(String id);
    public ApiResponse<String> updateFile(FileDocument fileInfo);
}
