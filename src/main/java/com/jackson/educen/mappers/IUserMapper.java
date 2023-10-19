package com.jackson.educen.mappers;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.models.User;

public interface IUserMapper {
    public User userDocumentToUser(UserDocument userDocument);
}
