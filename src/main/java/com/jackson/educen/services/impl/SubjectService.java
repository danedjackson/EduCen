package com.jackson.educen.services.impl;

import com.jackson.educen.documents.SubjectDocument;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.repositories.ISubjectRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ISubjectService;
import com.jackson.educen.utils.Util;
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
    public ApiResponse<List<SubjectDocument>> getAllSubjects() {
        try {
            List<SubjectDocument> subjectDocuments = subjectRepository.findAll();
            if (subjectDocuments.isEmpty()) {
                logger.errorLog("Could not fetch subject records from the database");
                return Util.failure(HttpStatus.NOT_FOUND, "Could not retrieve subjects from database");
            }
            logger.infoLog("Successfully fetch subject records from the database");
            return Util.success(subjectDocuments, "Successfully retrieved subject records from database");
        } catch (Exception e) {
            logger.errorLog("An error occurred while attempting to fetch subject records: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while trying to fetch subject records: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<SubjectDocument> saveSubject(String subjectName) {
        try {
            SubjectDocument subjectExists = subjectRepository.findBySubjectName(subjectName);
            if (subjectExists != null) {
                logger.errorLog(subjectName + " cannot be created. It already exists.");
                return Util.failure(HttpStatus.CONFLICT, "Record already exists");
            }
            subjectRepository.save(new SubjectDocument(subjectName));

            subjectExists = subjectRepository.findBySubjectName(subjectName);
            if (subjectExists == null) {
                logger.errorLog(subjectName + " was not saved successfully");
                return Util.failure(HttpStatus.NOT_FOUND, "Could not save record");
            }
            logger.infoLog("Successfully inserted subject '" + subjectExists.getSubjectName() + "' into the database");
            return Util.success(subjectExists, "Successfully created subject: {ID: "
                    + subjectExists.getId()
                    + ", Name: "
                    + subjectExists.getSubjectName()
                    + "}");
        } catch (Exception e) {
            logger.errorLog("An error occurred while saving subject '" + subjectName + "'. Exception: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the subject: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<SubjectDocument> removeSubject(String subjectName) {
        try {
            SubjectDocument subject = subjectRepository.findBySubjectName(subjectName);
            if (subject == null) {
                logger.errorLog(subjectName + " cannot be deleted. It does not exist.");
                return Util.failure(HttpStatus.NOT_FOUND, "Record does not exist");
            }
            subjectRepository.delete(subject);
            logger.infoLog("Successfully removed subject '" + subjectName + "'");
            return Util.success(subject, "Successfully removed '" + subjectName + "'");
        } catch (Exception e) {
            logger.errorLog("An error occurred while removing subject '" + subjectName + "'. Exception: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while removing the subject: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<SubjectDocument> editSubject(String oldSubjectName, String newSubjectName) {
        try {
            SubjectDocument existingSubject = subjectRepository.findBySubjectName(oldSubjectName);
            if (existingSubject == null) {
                logger.errorLog("Subject with name '" + oldSubjectName + "' does not exist and cannot be edited.");
                return Util.failure(HttpStatus.NOT_FOUND, "Subject with name '" + oldSubjectName + "' does not exist.");
            }

            SubjectDocument duplicateSubject = subjectRepository.findBySubjectName(newSubjectName);
            if (duplicateSubject != null) {
                logger.errorLog("Subject with name '" + newSubjectName + "' already exists. Cannot rename.");
                return Util.failure(HttpStatus.CONFLICT, "Subject with name '" + newSubjectName + "' already exists.");
            }

            existingSubject.setSubjectName(newSubjectName);
            subjectRepository.save(existingSubject);

            logger.infoLog("Successfully updated subject name from '" + oldSubjectName + "' to '" + newSubjectName + "'");
            return Util.success(existingSubject, "Successfully updated subject name.");
        } catch (Exception e) {
            logger.errorLog("An error occurred while editing subject name from '" + oldSubjectName + "' to '" + newSubjectName + "'. Exception: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while editing the subject name.");
        }
    }
}
