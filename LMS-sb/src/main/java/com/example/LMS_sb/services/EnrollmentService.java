package com.example.LMS_sb.services;

import com.example.LMS_sb.exceptions.CourseNotFoundException;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Enrollment;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.repository.CourseRepository;
import com.example.LMS_sb.repository.EnrollmentRepository;
import com.example.LMS_sb.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    public void enrollInACourse(Long courseId, Authentication authentication) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        boolean alreadyEnrolled = enrollmentRepository.findByStudentIdAndCourseId(student.getId(),course.getId()).isPresent();
        if(alreadyEnrolled) throw new IllegalStateException("Student already enrolled in this course");
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);
    }

    public List<Course> getCoursesByStudent(Student student) {
        return enrollmentRepository.findByStudent(student).stream().map(
                Enrollment::getCourse)
                .toList();

    }
}
