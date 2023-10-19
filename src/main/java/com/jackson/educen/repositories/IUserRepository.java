package com.jackson.educen.repositories;

import com.jackson.educen.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<UserDocument, String> {

}
