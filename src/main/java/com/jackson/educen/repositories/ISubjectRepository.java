package com.jackson.educen.repositories;

import com.jackson.educen.documents.SubjectDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ISubjectRepository extends MongoRepository<SubjectDocument, String> {
}
