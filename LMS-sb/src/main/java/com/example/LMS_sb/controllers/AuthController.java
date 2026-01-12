package com.example.LMS_sb.controllers;


import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.services.StudentService;
import com.example.LMS_sb.services.TeacherService;
import com.example.LMS_sb.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private StudentService studentService;
    private TeacherService teacherService;
    private UserService userService;


    @PostMapping("/api/auth/register/student")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerStudent(@RequestBody CreateStudentRequestDto dto){
              studentService.createStudent(dto);
              return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PostMapping("/api/auth/register/teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerTeacher(@RequestBody CreateTeacherRequestDto dto){
        teacherService.createTeacher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto dto){

        return ResponseEntity.ok(userService.authenticateUser(dto));

    }

}
