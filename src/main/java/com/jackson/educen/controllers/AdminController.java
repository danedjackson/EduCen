package com.jackson.educen.controllers;


import com.jackson.educen.models.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping
    public ApiResponse<String> sayHello(){
        return new ApiResponse<>(HttpStatus.OK, "HI ADMIN", "");
    }
}
