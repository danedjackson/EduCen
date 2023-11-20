package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.impl.UserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public ApiResponse<User> signup(UserDTO signUpRequest) {
        UserDocument userDocument;
        userDocument = userMapper.userDTOToUserDocument(signUpRequest);
        userDocument.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        UserDocument savedUser = userRepository.save(userDocument);

        return new ApiResponse<>(
                HttpStatus.CREATED,
                userMapper.userDocumentToUser(savedUser),
                "Successfully created a new user with ID: " + savedUser.getId()
        );
    }
}
