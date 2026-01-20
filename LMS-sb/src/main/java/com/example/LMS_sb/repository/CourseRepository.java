package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByTeacher(Teacher teacher);
}
