package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByUserEmail(String email);

}
