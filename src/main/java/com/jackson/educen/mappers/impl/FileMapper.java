package com.jackson.educen.mappers.impl;

import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.mappers.IFileMapper;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.User.User;
import org.springframework.stereotype.Component;

@Component
public class FileMapper implements IFileMapper {
    @Override
    public File fileDocumentToFile(FileDocument fileDocument) {
        if (fileDocument == null){
            return new File();
        }
        File file = new File();
        file.setId(fileDocument.getId());
        file.setTitle(fileDocument.getTitle());
        file.setTeacherId(fileDocument.getTeacherId());
        file.setComments(fileDocument.getComments());

        return file;
    }
}
