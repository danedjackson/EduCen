package com.jackson.educen.services;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.models.requests.UpdatePlanRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ITeacherService {
    public ApiResponse<String> uploadFile (MultipartFile file);
    public ApiResponse<FileDocument> getFile(String id);
    public ApiResponse<List<UserFile>> getAllTeacherPlans();
    public ApiResponse<String> updateFile(FileDocument fileInfo);
    public ApiResponse<List<FileDocument>> updateFileComments(UpdatePlanRequest planComments);
    public ApiResponse<User> getStudentById(String id);
    public ApiResponse<List<User>> getAllStudents();
    public ApiResponse<User> addNewStudent(UserDTO user);
    public ApiResponse<User> editStudentDetails(UserDTO user);

}
