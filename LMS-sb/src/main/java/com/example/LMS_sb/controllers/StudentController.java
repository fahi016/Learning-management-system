package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.StudentDto;
import com.example.LMS_sb.dtos.UpdateMeByStudentDto;
import com.example.LMS_sb.services.CourseService;
import com.example.LMS_sb.services.EnrollmentService;
import com.example.LMS_sb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor

public class StudentController {
    private StudentService studentService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private AssignmentService assignmentService;



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

    @GetMapping("api/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

   @PostMapping("api/courses/enroll/{courseId}")
    public ResponseEntity<?> EnrollInACourse(Authentication authentication,@PathVariable Long courseId){
        enrollmentService.enrollInACourse(courseId,authentication);
       return ResponseEntity.status(HttpStatus.CREATED).body("Enrolled successfully");

   }

    @GetMapping("api/students/courses")
    public ResponseEntity<?> getAllMyCourses(Authentication authentication){
        return ResponseEntity.ok(courseService.getAllMyCourses(authentication));
    }

    @GetMapping("api/students/assignments")
    public ResponseEntity<?> viewMyAssignments(Authentication authentication){
        return ResponseEntity.ok(assignmentService.getMyAssignments(authentication));
    }
}
