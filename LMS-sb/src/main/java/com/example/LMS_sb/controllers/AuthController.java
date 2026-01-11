package com.example.LMS_sb.controllers;


import com.example.LMS_sb.dtos.AdminDto;
import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.services.AdminService;
import com.example.LMS_sb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private StudentService studentService;
    private AdminService adminService;

    @PostMapping("/api/auth/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDto dto){

        return ResponseEntity.ok(adminService.authenticateAdmin(dto));

    }

    @PostMapping("/api/admin/users/student")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerStudent(@RequestBody CreateStudentRequestDto dto){
              studentService.createStudent(dto);
              return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
