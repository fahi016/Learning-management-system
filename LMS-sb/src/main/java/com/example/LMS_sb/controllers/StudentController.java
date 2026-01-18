package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.StudentDto;
import com.example.LMS_sb.dtos.UpdateMeByStudentDto;
import com.example.LMS_sb.services.CourseService;
import com.example.LMS_sb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor

public class StudentController {
    StudentService studentService;
    CourseService courseService;

    @GetMapping("/api/students/me")
    public ResponseEntity<?> getStudentProfile(Authentication authentication){
        return ResponseEntity.ok(studentService.getMyProfile(authentication));

    }

    @PutMapping("/api/students/me")
    public ResponseEntity<?> updateStudentProfile(@RequestBody UpdateMeByStudentDto dto, Authentication authentication){
        studentService.updateMyProfile(dto,authentication);
        return ResponseEntity.ok("Your profile updated successfully");

    }

    @GetMapping("api/courses")
    public ResponseEntity<?> getAllCourses(){
      return ResponseEntity.ok(courseService.getAllCourses());
    }
}
