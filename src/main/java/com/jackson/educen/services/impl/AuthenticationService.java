package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.impl.UserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.models.dto.RefreshToken.RefreshTokenRequest;
import com.jackson.educen.models.dto.SignIn.SignInRequest;
import com.jackson.educen.models.dto.SignIn.SignInResponse;
import com.jackson.educen.models.dto.UserDTO;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.IAuthenticationService;
import com.jackson.educen.services.IJwtService;
import com.jackson.educen.services.ILogger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final UserMapper userMapper;
    private final ILogger logger;

    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, IJwtService jwtService, UserMapper userMapper, ILogger logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.logger = logger;
    }

    public ApiResponse<User> signUp(UserDTO signUpRequest) {
        UserDocument userDocument = userMapper.userDTOToUserDocument(signUpRequest);
        userDocument.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        //TODO: Check if user exists before saving!!!
        UserDocument savedUser = userRepository.save(userDocument);

        return new ApiResponse<>(
                HttpStatus.CREATED,
                userMapper.userDocumentToUser(savedUser),
                "Successfully created a new user with ID: " + savedUser.getId()
        );
    }

    public ApiResponse<SignInResponse> signIn(SignInRequest signInRequest) {
        UserDocument user;
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(), signInRequest.getPassword()));

            user = userRepository.findByEmail(signInRequest.getEmail());

            if (null == user.getId()) {
                return new ApiResponse<>(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Invalid email or password provided"
                );
            }

            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            SignInResponse signInResponse = new SignInResponse();
            signInResponse.setId(user.getId());
            signInResponse.setFirstName(user.getFirstName());
            signInResponse.setRole(user.getRole());
            signInResponse.setToken(jwt);
            signInResponse.setRefreshToken(refreshToken);

            return new ApiResponse<>(
                    HttpStatus.FOUND,
                    signInResponse,
                    "User validated"
            );

        }
        catch(BadCredentialsException e) {
            logger.errorLog("An error occurred during authentication of user " + signInRequest.getEmail()+": " +e.getMessage());
            return new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
                    null,
                    "Incorrect Credentials"
            );
        }catch(Exception e) {
            logger.errorLog("An error occurred during authentication of user " + signInRequest.getEmail()+": " +e.getMessage());
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred during authentication"
            );
        }
    }

    @Override
    public ApiResponse<SignInResponse> resfreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserDocument user = userRepository.findByEmail(userEmail);
        if(null == user) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Could not validate user"
            );
        }
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            // Refresh Token valid and user exists
            var jwt = jwtService.generateToken(user);

            SignInResponse signInResponse = new SignInResponse();
            signInResponse.setToken(jwt);
            signInResponse.setRefreshToken(refreshTokenRequest.getToken());

            return new ApiResponse<>(
                    HttpStatus.FOUND,
                    signInResponse,
                    "Validated refresh token"
            );
        }
        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                "Something went wrong querying refresh token"
        );
    }
}
