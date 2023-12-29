package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.repositories.IFileRepository;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ITeacherService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements ITeacherService {

    private final IFileRepository documentRepository;
    private final IUserRepository userRepository;
    private final UserService userService;
    private final IUserMapper userMapper;
    private final ILogger logger;

    public TeacherService(IFileRepository documentRepository, IUserRepository userRepository, UserService userService, IUserMapper userMapper, ILogger logger) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.logger = logger;
    }
    @Override
    public ApiResponse<String> uploadFile(MultipartFile file) {
        String teacherId = file.getOriginalFilename();
        if (teacherId == null || teacherId.isEmpty()) {
            logger.infoLog("Invalid file name for file provided");
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not determine file name."
            );
        }
        Optional<UserDocument> document = userRepository.findById(cleanFileName(teacherId));
        if(document.isEmpty()){
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not find teacher information."
            );
        }
        UserDocument teacherDetails = document.get();
        FileDocument fileDocument = new FileDocument();
        try {
            fileDocument.setDateUploaded(LocalDate.now());
            fileDocument.setTitle(fileDocument.getDateUploaded()
                    + "_"
                    + teacherDetails.getFirstName()
                    + "_"
                    + teacherDetails.getLastName()
                    + "_"
                    + teacherDetails.getGrade()
            );
            fileDocument.setTeacherId(teacherDetails.getId());
            fileDocument.setDocument(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

            // Storing the file to db
            fileDocument = documentRepository.save(fileDocument);

        } catch(IOException e) {
            logger.errorLog("Could not get bytes from file: " + e.getMessage());
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Something went wrong while setting Document before storage."
            );
        } catch(Exception e) {
            logger.errorLog("Something went wrong saving file: " + e.getMessage());
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Something went wrong while attempting to save the file."
            );
        }
        logger.infoLog("Stored document with ID: " + teacherDetails.getId());
        return new ApiResponse<>(
                HttpStatus.OK,
                fileDocument.getId(),
                "Successfully stored document to database"
        );
    }

    @Override
    public ApiResponse<FileDocument> getFile(String id) {
        Optional<FileDocument> document = documentRepository.findById(id);
        if(document.isEmpty()) {
            logger.errorLog("Could not retrieve document with ID: " + id);
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Unable to retrieve document"
            );
        }
        FileDocument fileDocument = document.get();
        return new ApiResponse<>(
                HttpStatus.OK,
                fileDocument,
                "Retrieved document"
        );
    }

    @Override
    public ApiResponse<String> updateFile(FileDocument fileInfo) {
        Optional<FileDocument> document = documentRepository.findById(cleanFileName(fileInfo.getId()));
        if(document.isEmpty()){
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not find file information."
            );
        }
        FileDocument fileDocument = document.get();
        fileDocument.setComments(fileInfo.getComments());
        fileDocument.setChecked(true);
        fileDocument.setDateModified(LocalDateTime.now());

        documentRepository.save(fileDocument);

        return new ApiResponse<>(
                HttpStatus.OK,
                fileDocument.getId(),
                "Updated record for document"
        );
    }

    @Override
    public ApiResponse<User> getStudentById(String id) {
        return userService.getUserById(id);
    }

    @Override
    public ApiResponse<List<User>> getAllStudents() {
        return userService.getAllUsersByRole(Role.STUDENT.name());
    }

    @Override
    public ApiResponse<User> addNewStudent(UserDTO user) {
        user.setRole(Role.STUDENT);
        return userService.addNewUser(user);
    }

    @Override
    public ApiResponse<User> editStudentDetails(UserDTO user) {
        return userService.editUserDetails(user);
    }


    private String cleanFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName;
        }
    }
}
