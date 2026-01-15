package com.example.LMS_sb.controllers;

import com.example.LMS_sb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor

public class StudentController {
    StudentService studentService;

    @GetMapping("/api/students/me")
    public ResponseEntity<?> getStudentProfile(Authentication authentication){
        return ResponseEntity.ok(studentService.getMyProfile(authentication));

    }
}
