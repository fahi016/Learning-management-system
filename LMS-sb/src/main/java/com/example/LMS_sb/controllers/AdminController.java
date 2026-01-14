package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.GetUserDto;
import com.example.LMS_sb.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        return ResponseEntity.ok(adminService.getUserById(id));

    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable long id, @RequestBody GetUserDto dto){
        adminService.updateUserById(id,dto);
        return ResponseEntity.ok("User updated successfully");

    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        adminService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");

    }


}
