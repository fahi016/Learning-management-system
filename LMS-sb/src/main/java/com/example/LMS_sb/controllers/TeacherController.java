package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.UpdateMeByStudentDto;
import com.example.LMS_sb.dtos.UpdateMeByTeacherDto;
import com.example.LMS_sb.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private TeacherService teacherService;

    @GetMapping("/api/teachers/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication){
        return ResponseEntity.ok(teacherService.getMyProfile(authentication));

    }

    @PutMapping("/api/teachers/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody UpdateMeByTeacherDto dto, Authentication authentication){
        teacherService.updateMyProfile(dto,authentication);
        return ResponseEntity.ok("Your profile updated successfully");

    }

}
