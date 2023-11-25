package com.jackson.educen.services;

import com.jackson.educen.documents.UserDocument;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface IJwtService {
    String extractUsername(String token);
    String generateToken(UserDocument userDocument);
    public boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> extraClaims, UserDocument userDocument);
}
