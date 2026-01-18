package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
