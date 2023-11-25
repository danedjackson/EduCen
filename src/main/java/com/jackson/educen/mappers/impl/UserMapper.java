package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.User;
import com.jackson.educen.models.dto.UserDTO;
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
        user.setAccess(userDocument.getAccess());
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
        userDocument.setAccess(userDTO.getAccess());
        userDocument.setEmail(userDTO.getEmail());
        userDocument.setType(userDTO.getType());
        userDocument.setDateOfBirth(userDTO.getDateOfBirth());
        userDocument.setZipCode(userDTO.getZipCode());
        userDocument.setRole(userDTO.getRole());
        userDocument.setDateRegistered(LocalDate.now());
        userDocument.setUsername(userDTO.getUsername());

        return userDocument;
    }
}
