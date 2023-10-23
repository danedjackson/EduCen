package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public UserService(IUserRepository userRepository, IUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<User> getUserById(String id) {
        Optional<UserDocument> userDocument =  userRepository.findById(id);
        if (userDocument.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    ("Could not find user with ID " + id)
            );
        }

        return new ApiResponse<>(
                HttpStatus.OK,
                userMapper.userDocumentToUser(userDocument.get()),
                "Found"
        );
    }

    @Override
    public ApiResponse<List<User>> getAllUsersOfType(int typeId) {
        List<UserDocument> userDocumentList = userRepository.findAllUsersGivenTypeId(typeId);
        if(userDocumentList.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not find any records for given type ID"
            );
        }
        List<User> userList = new ArrayList<>();
        userDocumentList.forEach(userDocument -> {
            userList.add(userMapper.userDocumentToUser(userDocument));
        });

        return new ApiResponse<>(
                HttpStatus.OK,
                userList,
                "Records found for type ID: " + typeId
        );
    }

    @Override
    public ApiResponse<User> addNewUser(UserDTO user) {
        UserDocument savedDocument = userRepository.save(userMapper.userDTOToUserDocument(user));
        if(null == savedDocument.getId()) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Could not insert user record"
            );
        }

        return new ApiResponse<>(
                HttpStatus.CREATED,
                userMapper.userDocumentToUser(savedDocument),
                "Successfully created user with ID: " + savedDocument.getId()
        );
    }

    @Override
    public ApiResponse<User> editUserDetails(UserDTO user) {
        return null;
    }
}
