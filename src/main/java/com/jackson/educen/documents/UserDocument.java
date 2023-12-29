package com.jackson.educen.documents;

import com.jackson.educen.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDocument implements UserDetails {
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
    @Field("grade")
    private String grade;
    @Field("username")
    private String username;
    @Field("password")
    private String password;
    @Field("role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //TODO: Sort out these please
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
