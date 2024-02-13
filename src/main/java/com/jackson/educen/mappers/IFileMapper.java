package com.jackson.educen.mappers;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.dto.File;

public interface IFileMapper {
    public File fileDocumentToFile(FileDocument fileDocument);
}
