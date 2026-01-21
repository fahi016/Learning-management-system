package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.AssignmentDto;
import com.example.LMS_sb.dtos.CreateUpdateAssignmentDto;
import com.example.LMS_sb.exceptions.AssignmentNotFoundException;
import com.example.LMS_sb.exceptions.CourseExcpetion;
import com.example.LMS_sb.models.Assignment;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.repository.AssignmentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AssignmentService {
    private StudentService studentService;
    private  AssignmentRepository assignmentRepository;
    private EnrollmentService enrollmentService;
    private CourseService courseService;
    private TeacherService teacherService;
    public List<AssignmentDto> getStudentAssignments(Authentication authentication){
        Student student = studentService.getStudentByUserEmail(authentication.getName());
        List<Course> courses = enrollmentService.getCoursesByStudent(student);
        if (courses.isEmpty()) {
            return List.of();
        }
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

    public AssignmentDto getStudentAssignmentById(Long assignmentId,Authentication authentication) {
        Student student = studentService.getStudentByUserEmail(authentication.getName());
        Assignment assignment =  assignmentRepository.findById(assignmentId).orElseThrow(AssignmentNotFoundException::new);
        Course course = assignment.getCourse();
        boolean isEnrolled = enrollmentService.isStudentEnrolled(student.getId(), course.getId());
        if (!isEnrolled) {
            throw new AccessDeniedException(
                    "Student is not enrolled in this course"
            );
        }
        return new AssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getCourse().getTitle(),
                assignment.getDueDate()
        );

    }

    public Assignment getAssignmentById(@NotNull Long assignmentId) {
        return assignmentRepository.findById(assignmentId).orElseThrow(AssignmentNotFoundException::new);
    }
    @Transactional
    public void createAssignment(Authentication authentication, Long courseId, CreateUpdateAssignmentDto dto) {
         Course course = courseService.findCourseById(courseId);
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new CourseExcpetion("Teacher is not assigned to this course");
        }

        Assignment assignment = new Assignment();
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setCourse(course);
        assignment.setDueDate(dto.getDueDate());

        assignmentRepository.save(assignment);
    }

    public List<AssignmentDto> getTeacherAssignments(Authentication authentication) {

        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        List<Course> courses = courseService.getCoursesByTeacher(teacher);
        if (courses.isEmpty()) {
            return List.of();
        }
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

    public AssignmentDto getTeacherAssignmentById(Long assignmentId,Authentication authentication) {
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Assignment assignment =  assignmentRepository.findById(assignmentId).orElseThrow(AssignmentNotFoundException::new);
        Course course = assignment.getCourse();
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new AccessDeniedException("Teacher does not own this assignment");
        }
        return new AssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getCourse().getTitle(),
                assignment.getDueDate()
        );

    }
    @Transactional
    public void editAssignment(Authentication authentication, Long assignmentId, CreateUpdateAssignmentDto dto) {
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Assignment assignment =  assignmentRepository.findById(assignmentId).orElseThrow(AssignmentNotFoundException::new);
        Course course = assignment.getCourse();
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new AccessDeniedException("Teacher does not own this assignment");
        }

        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setCourse(course);
        assignment.setDueDate(dto.getDueDate());
        assignmentRepository.save(assignment);
    }
}
