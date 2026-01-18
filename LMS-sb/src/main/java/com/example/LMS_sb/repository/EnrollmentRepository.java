package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Enrollment;
import com.example.LMS_sb.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findAllByStudentId(Long studentId);
    List<Enrollment> findByStudent(Student student);
}
