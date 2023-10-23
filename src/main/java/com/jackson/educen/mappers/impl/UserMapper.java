package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.User;
import com.jackson.educen.utils.Util;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class UserMapper implements IUserMapper {
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
        user.setAccess(userDocument.getAccess());

        if(null != user.getDateOfBirth()) {
            user.setAge(new Util().getAge(LocalDate.now(), user.getDateOfBirth()));
        }

        return user;
    }
}
