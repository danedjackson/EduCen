package com.jackson.educen.mappers;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.models.dto.User;
import com.jackson.educen.models.dto.UserDTO;

public interface IUserMapper {
    public User userDocumentToUser(UserDocument userDocument);
    public UserDocument userDTOToUserDocument(UserDTO userDTO);
}
