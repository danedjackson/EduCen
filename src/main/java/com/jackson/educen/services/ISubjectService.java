package com.jackson.educen.services;

import com.jackson.educen.documents.SubjectDocument;
import com.jackson.educen.models.ApiResponse;

import java.util.List;

public interface ISubjectService {
    ApiResponse<List<SubjectDocument>> getAllSubjects();
    ApiResponse<SubjectDocument> saveSubject(String subjectName);
    ApiResponse<SubjectDocument> removeSubject(String subjectName);
    ApiResponse<SubjectDocument> editSubject(String oldSubjectName, String newSubjectName);
}
