package com.example.LMS_sb.controllers;

import com.example.LMS_sb.dtos.AddUpdateLectureDto;
import com.example.LMS_sb.dtos.CreateUpdateAssignmentDto;
import com.example.LMS_sb.dtos.UpdateMeByTeacherDto;
import com.example.LMS_sb.services.AssignmentService;
import com.example.LMS_sb.services.CourseService;
import com.example.LMS_sb.services.LectureService;
import com.example.LMS_sb.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private TeacherService teacherService;
    private CourseService courseService;
    private LectureService lectureService;
    private AssignmentService assignmentService;

    @GetMapping("/api/teachers/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication){
        return ResponseEntity.ok(teacherService.getMyProfile(authentication));

    }

    @PutMapping("/api/teachers/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody UpdateMeByTeacherDto dto, Authentication authentication){
        teacherService.updateMyProfile(dto,authentication);
        return ResponseEntity.ok("Your profile updated successfully");

    }

    @GetMapping("/api/teachers/courses")
    public ResponseEntity<?> getMyCourses(Authentication authentication){
        return ResponseEntity.ok(courseService.getTeacherCourses(authentication));
    }

    @PostMapping("/api/teachers/courses/{courseId}/lectures")
    public ResponseEntity<?> addLecture(@PathVariable Long courseId, @RequestBody AddUpdateLectureDto dto, Authentication authentication){
        lectureService.addLecture(courseId,authentication,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Lecture created successfully");
    }

    @GetMapping("/api/teachers/courses/{courseId}/lectures")
    public ResponseEntity<?> viewLectures(Authentication authentication,@PathVariable Long courseId){
        return ResponseEntity.ok(lectureService.viewLecturesByTeacher(authentication,courseId));
    }

    @GetMapping("/api/teachers/courses/{courseId}/lectures/{lectureId}")
    public ResponseEntity<?> viewLecture(Authentication authentication,@PathVariable Long courseId,@PathVariable Long lectureId){
        return ResponseEntity.ok(lectureService.viewLectureByTeacher(authentication,courseId,lectureId));
    }

    @PutMapping("/api/teachers/courses/{courseId}/lectures/{lectureId}")
    public ResponseEntity<?> editLecture(Authentication authentication,@PathVariable Long courseId,@PathVariable Long lectureId,@RequestBody AddUpdateLectureDto dto){
        lectureService.editLecture(authentication,courseId,lectureId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Lecture updated successfully");
    }

    @DeleteMapping("/api/teachers/courses/{courseId}/lectures/{lectureId}")
    public ResponseEntity<?> deleteLecture(Authentication authentication,@PathVariable Long courseId,@PathVariable Long lectureId,@RequestBody AddUpdateLectureDto dto){
        lectureService.deleteLecture(authentication,courseId,lectureId,dto);
        return ResponseEntity.ok("Lecture deleted successfully");
    }

    @PostMapping("/api/teachers/courses/{courseId}/assignments")
    public ResponseEntity<?> createAssignment(Authentication authentication, @PathVariable Long courseId, @RequestBody CreateUpdateAssignmentDto dto){
        assignmentService.createAssignment(authentication,courseId,dto);
        return ResponseEntity.ok("Assignment created successfully");

    }

    @GetMapping("/api/teachers/assignments")
    public ResponseEntity<?> getTeacherAssignments(Authentication authentication){
        return ResponseEntity.ok(assignmentService.getTeacherAssignments(authentication));

    }

    @GetMapping("/api/teachers/assignments/{assignmentId}")
    public ResponseEntity<?> getTeacherAssignmentById(@PathVariable Long assignmentId,Authentication authentication){
        return ResponseEntity.ok(assignmentService.getTeacherAssignmentById(assignmentId,authentication));

    }

    @PutMapping("/api/assignments/{assignmentId}")
    public ResponseEntity<?> editAssignment(Authentication authentication,@PathVariable Long assignmentId,@RequestBody CreateUpdateAssignmentDto dto){
        assignmentService.editAssignment(authentication,assignmentId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Assignment updated successfully");
    }


}
