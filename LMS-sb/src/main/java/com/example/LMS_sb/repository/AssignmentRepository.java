package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Assignment;
import com.example.LMS_sb.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourseIn(List<Course> courses);
}
