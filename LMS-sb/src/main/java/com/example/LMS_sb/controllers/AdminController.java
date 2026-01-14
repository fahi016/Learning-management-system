package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.GetUserDto;
import com.example.LMS_sb.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor

public class AdminController {
    AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
       return ResponseEntity.ok(adminService.getAllUsers());

    }


}
