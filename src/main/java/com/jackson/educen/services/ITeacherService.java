package com.jackson.educen.services;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.models.requests.UpdatePlanRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITeacherService {
    public ApiResponse<String> uploadFile (MultipartFile file);
    public ApiResponse<FileDocument> getLessonPlan(String id);
    public ApiResponse<List<FileDocument>> getLessonPlans(String id);
    public ApiResponse<List<UserFile>> getAllTeacherPlans();
    public ApiResponse<String> updateFile(FileDocument fileInfo);
    public ApiResponse<List<FileDocument>> updateFileComments(UpdatePlanRequest planComments);
    public ApiResponse<User> getStudentById(String id);
    public ApiResponse<List<User>> getAllStudents();
    public ApiResponse<User> addNewStudent(UserDTO user);
    public ApiResponse<User> editStudentDetails(UserDTO user);

}
