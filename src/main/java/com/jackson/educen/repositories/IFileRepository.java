package com.jackson.educen.repositories;

import com.jackson.educen.documents.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFileRepository extends MongoRepository<FileDocument, String> {
    Optional<FileDocument> findAllByTeacherId(String teacherId);
}
