package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final ILogger logger;

    public UserService(IUserRepository userRepository, IUserMapper userMapper, ILogger logger) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.logger = logger;
    }

    // JWT
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                return userRepository.findByEmail(userEmail);
            }
        };
    }

    @Override
    public ApiResponse<User> getUserById(String id) {
        Optional<UserDocument> userDocument =  userRepository.findById(id);
        if (userDocument.isEmpty()) {
            logger.infoLog("User Document with ID " + id +" could not be found in the database");
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    ("Could not find user with ID " + id)
            );
        }

        logger.infoLog("Returning User Document with ID " + id +" from the database");
        return new ApiResponse<>(
                HttpStatus.OK,
                userMapper.userDocumentToUser(userDocument.get()),
                "Found"
        );
    }

    @Override
    public ApiResponse<List<User>> getAllUsersByRole(String role) {
        List<UserDocument> userDocumentList = userRepository.findAllUsersGivenRole(role);
        if(userDocumentList.isEmpty()) {
            logger.infoLog("User Document with role " + role +" could not be found in the database");
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not find any records for given role"
            );
        }
        List<User> userList = new ArrayList<>();
        userDocumentList.forEach(userDocument -> {
            userList.add(userMapper.userDocumentToUser(userDocument));
        });

        logger.infoLog("Returning all users with role " + role + " from the database");
        return new ApiResponse<>(
                HttpStatus.OK,
                userList,
                "Records found for role: " + role
        );
    }

    @Override
    public ApiResponse<User> addNewUser(UserDTO user) {
        user.setFirstName(user.getFirstName().trim());
        user.setLastName(user.getLastName().trim());
        UserDocument savedDocument = userRepository.findByEmail(user.getEmail());
        // If email is not found, check if the user's first and last name exists
        if(savedDocument == null) {
            savedDocument = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        }
        if(savedDocument != null) {
            logger.errorLog("Count not add a new user as user already exists");
            return new ApiResponse<>(
                    HttpStatus.CONFLICT,
                    null,
                    "Record already exists"
            );
        }
        savedDocument = userRepository.save(userMapper.userDTOToUserDocument(user));
        if(null == savedDocument.getId()) {
            logger.errorLog("Could not insert user information with name '"+ user.getFirstName() +"' in the database");
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Could not insert user record"
            );
        }
        logger.infoLog("Successfully inserted user with name '"+ user.getFirstName() +"' into the database");
        return new ApiResponse<>(
                HttpStatus.CREATED,
                userMapper.userDocumentToUser(savedDocument),
                "Successfully created user with ID: " + savedDocument.getId()
        );
    }

    @Override
    public ApiResponse<User> editUserDetails(UserDTO user) {
        if(!userRepository.existsById(user.getId())) {
            logger.errorLog("Could not find user with the ID '" +user.getId()+"' to edit");
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "No records found with given ID: " + user.getId()
            );
        }
        logger.infoLog("User information was found for ID '"+user.getId()+"'. Editing information");
        UserDocument savedDocument = userRepository.save(userMapper.userDTOToUserDocument(user));
        logger.infoLog("Successfully edited information for user ID '" + user.getId() + "'. Returning updated record.");
        return new ApiResponse<>(
                HttpStatus.OK,
                userMapper.userDocumentToUser(savedDocument),
                "Successfully edited record with ID: " + savedDocument.getId()
        );
    }
}
