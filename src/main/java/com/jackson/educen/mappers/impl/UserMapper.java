package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.utils.Util;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class UserMapper implements IUserMapper {
    public User userDocumentToUser(UserDocument userDocument) {
        if (userDocument.getId() == null){
            return new User();
        }
        User user = new User();
        user.setId(userDocument.getId());
        user.setFirstName(userDocument.getFirstName());
        user.setMiddleName(userDocument.getMiddleName());
        user.setLastName(userDocument.getLastName());
        user.setCity(userDocument.getCity());
        user.setEmail(userDocument.getEmail());
        user.setAddress(userDocument.getAddress());
        user.setContactNumber(userDocument.getContactNumber());
        user.setDateOfBirth(userDocument.getDateOfBirth());
        user.setZipCode(userDocument.getZipCode());
        user.setRole(userDocument.getRole());
        user.setGrade(userDocument.getGrade());

        if(null != user.getDateOfBirth()) {
            user.setAge(new Util().getAge(LocalDate.now(), user.getDateOfBirth()));
        }

        return user;
    }

    @Override
    public UserDocument userDTOToUserDocument(UserDTO userDTO) {
        if(null == userDTO.getFirstName()) {
            return new UserDocument();
        }

        UserDocument userDocument = new UserDocument();

        if(userDTO.getId() != null) {
            userDocument.setId(userDTO.getId());
        }
        userDocument.setFirstName(userDTO.getFirstName());
        userDocument.setMiddleName(userDTO.getMiddleName());
        userDocument.setLastName(userDTO.getLastName());
        userDocument.setContactNumber(userDTO.getContactNumber());
        userDocument.setCity(userDTO.getCity());
        userDocument.setAddress(userDTO.getAddress());
        userDocument.setGrade(userDTO.getGrade());
        userDocument.setEmail(userDTO.getEmail());
        userDocument.setDateOfBirth(userDTO.getDateOfBirth());
        userDocument.setZipCode(userDTO.getZipCode());
        userDocument.setRole(userDTO.getRole());
        userDocument.setDateRegistered(LocalDate.now());

        // Setting username to Email. TODO: If changing from email to username, this needs editing
        userDocument.setUsername(userDTO.getEmail());

        return userDocument;
    }

    @Override
    public UserFile userDocumentToUserFile(UserDocument userDocument) {
        if (userDocument.getId() == null){
            return new UserFile();
        }
        UserFile userFile = new UserFile();
        userFile.setId(userDocument.getId());
        userFile.setFirstName(userDocument.getFirstName());
        userFile.setMiddleName(userDocument.getMiddleName());
        userFile.setLastName(userDocument.getLastName());
        userFile.setEmail(userDocument.getEmail());
        userFile.setGrade(userDocument.getGrade());

        return userFile;
    }
}
