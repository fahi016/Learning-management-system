package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.services.AdminService;
import com.example.LMS_sb.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor

public class AdminController {
    AdminService adminService;
    CourseService courseService;

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
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.ok(adminService.getAllStudents());

    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getStudentById(id));

    }
    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable Long id, @RequestBody StudentDto dto){
        adminService.updateStudentById(id,dto);
        return ResponseEntity.ok("Student updated successfully");

    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id){
        adminService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted");

    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers(){
        return ResponseEntity.ok(adminService.getAllTeachers());

    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTeacherById(id));

    }
    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> updateTeacherById(@PathVariable Long id, @RequestBody TeacherDto dto){
        adminService.updateTeacherById(id,dto);
        return ResponseEntity.ok("Teacher updated successfully");

    }
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable Long id){
        adminService.deleteTeacherById(id);
        return ResponseEntity.ok("Teacher deleted");

    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto dto){
        courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Course created successfully");

    }
    @GetMapping("/courses")
    public ResponseEntity<?> getCourseById(){
        return ResponseEntity.ok(courseService.getAllCourses());

    }
    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));

    }
    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable Long id, @RequestBody CourseDto dto){
        courseService.updateCourseById(id,dto);
        return ResponseEntity.ok("Course updated successfully");

    }
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return ResponseEntity.ok("Course deleted");

    }


}
