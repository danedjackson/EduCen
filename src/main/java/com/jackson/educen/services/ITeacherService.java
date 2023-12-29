package com.jackson.educen.services;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITeacherService {
    public ApiResponse<String> uploadFile (MultipartFile file);
    public ApiResponse<FileDocument> getFile(String id);
    ApiResponse<List<User>> getAllTeacherPlans();
    public ApiResponse<String> updateFile(FileDocument fileInfo);
    public ApiResponse<User> getStudentById(String id);
    public ApiResponse<List<User>> getAllStudents();
    public ApiResponse<User> addNewStudent(UserDTO user);
    public ApiResponse<User> editStudentDetails(UserDTO user);

}
