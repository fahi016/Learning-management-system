package com.example.LMS_sb.controllers;


import com.example.LMS_sb.dtos.AssignmentDto;
import com.example.LMS_sb.exceptions.AssignmentNotFoundException;
import com.example.LMS_sb.models.Assignment;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.repository.AssignmentRepository;
import com.example.LMS_sb.services.CourseService;
import com.example.LMS_sb.services.EnrollmentService;
import com.example.LMS_sb.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AssignmentService {
    private StudentService studentService;
    private  CourseService courseService;
    private  AssignmentRepository assignmentRepository;
    private EnrollmentService enrollmentService;
    public List<AssignmentDto> getMyAssignments(Authentication authentication){
        Student student = studentService.getStudentByUserEmail(authentication.getName());
        List<Course> courses = enrollmentService.getCoursesByStudent(student);
        return assignmentRepository.findByCourseIn(courses).stream().map(assignment ->
                  new AssignmentDto(
                          assignment.getId(),
                          assignment.getTitle(),
                          assignment.getDescription(),
                          assignment.getCourse().getTitle(),
                          assignment.getDueDate()

                  )
                ).toList();

    }

    public AssignmentDto getMyAssignmentById(Long id) {
        Assignment assignment =  assignmentRepository.findById(id).orElseThrow(AssignmentNotFoundException::new);
        return new AssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getCourse().getTitle(),
                assignment.getDueDate()
        );

    }
}
