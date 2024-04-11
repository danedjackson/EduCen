package com.jackson.educen.repositories;

import com.jackson.educen.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<UserDocument, String> {
    @Query("{'role': ?0}")
    List<UserDocument> findAllUsersGivenRole(String role);
    UserDocument findByEmail(String email);
    UserDocument findByFirstName(String firstName);
    UserDocument findByLastName(String lastName);

    // Custom query for case-insensitive search by first name and last name
    @Query("{'firstName': {$regex : ?0, $options: 'i'}, 'lastName': {$regex : ?1, $options: 'i'}}")
    UserDocument findByFirstNameAndLastName(String firstName, String lastName);
}
