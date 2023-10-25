package com.jackson.educen.services.impl;

import com.jackson.educen.documents.SubjectDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.repositories.ISubjectRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ISubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectService implements ISubjectService {
    private final ISubjectRepository subjectRepository;
    private final ILogger logger;

    public SubjectService(ISubjectRepository subjectRepository, ILogger logger) {
        this.subjectRepository = subjectRepository;
        this.logger = logger;
    }

    @Override
    public ApiResponse<List<String>> getAllSubjects() {
        try {
            SubjectDocument subjectDocument = subjectRepository.findAll().get(0);
            if (null == subjectDocument.getSubjects()) {
                logger.errorLog("Could not fetch subject records from the database");
                return new ApiResponse<>(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Could not retrieve subjects from database"
                );
            }
            logger.infoLog("Successfully fetch subject records from the database");
            return new ApiResponse<>(
                    HttpStatus.OK,
                    subjectDocument.getSubjects(),
                    "Successfully retrieved subject records from database"
            );
        }catch(Exception e) {
            logger.errorLog("An error occurred while attempting to fetch subject records: " + e.getMessage());
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred while trying to fetch subject records: " + e.getMessage()
            );
        }
    }
}
