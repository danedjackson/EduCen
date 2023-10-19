package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.impl.UserMapperImpl;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
        UserMapperImpl mapper = new UserMapperImpl();

        return new ApiResponse<>(
                HttpStatus.OK,
                mapper.userDocumentToUser(userDocument.get()),
                "Found"
        );
    }

    @Override
    public ApiResponse<User> addNewUser(User user) {
        return null;
    }

    @Override
    public ApiResponse<User> editUserDetails(User user) {
        return null;
    }
}
