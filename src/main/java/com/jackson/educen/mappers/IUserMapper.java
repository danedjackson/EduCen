package com.jackson.educen.mappers;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;

public interface IUserMapper {
    public User userDocumentToUser(UserDocument userDocument);
    public UserDocument userDTOToUserDocument(UserDTO userDTO);
}
