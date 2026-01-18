package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Assignment;
import com.example.LMS_sb.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourseIn(List<Course> courses);
}
