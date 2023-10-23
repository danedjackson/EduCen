package com.jackson.educen.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDocument {
    @Id
    private String id;
    @Field("first_name")
    private String firstName;
    @Field("middle_name")
    private String middleName;
    @Field("last_name")
    private String lastName;
    @Field("date_of_birth")
    private LocalDate dateOfBirth;
    @Field("address")
    private String address;
    @Field("contact_no")
    private String contactNumber;
    @Field("email")
    private String email;
    @Field("city")
    private String city;
    @Field("zip")
    private String zipCode;
    @Field("date_registered")
    private LocalDate dateRegistered;
    @Field("type")
    private int type;
    @Field("access")
    private String access;
}
