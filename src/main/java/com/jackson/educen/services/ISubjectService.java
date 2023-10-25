package com.jackson.educen.services;

import com.jackson.educen.models.ApiResponse;

import java.util.List;

public interface ISubjectService {
    ApiResponse<List<String>> getAllSubjects();
}
