package com.jackson.educen.repositories;

import com.jackson.educen.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<UserDocument, String> {
    @Query("{'type': ?0}")
    List<UserDocument> findAllUsersGivenTypeId(int type);
    UserDocument findByEmail(String email);
}
