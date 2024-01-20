package com.jackson.educen.models.dto.User;

import com.jackson.educen.models.dto.File;
import lombok.Data;

import java.util.List;

@Data
public class UserFile{
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String grade;
    private List<File> lessonPlans;
}
