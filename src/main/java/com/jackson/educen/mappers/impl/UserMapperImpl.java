package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements IUserMapper {
    public User userDocumentToUser(UserDocument userDocument) {
        User user = new User();
        user.setFirstName(userDocument.getFirstName());
        user.setMiddleName(userDocument.getMiddleName());
        user.setLastName(userDocument.getLastName());
        user.setCity(userDocument.getCity());
        user.setEmail(userDocument.getEmail());
        user.setAddress(userDocument.getAddress());
        user.setContactNumber(userDocument.getContactNumber());
        user.setDateOfBirth(userDocument.getDateOfBirth());
        user.setZipCode(userDocument.getZipCode());

        return user;
    }
}
