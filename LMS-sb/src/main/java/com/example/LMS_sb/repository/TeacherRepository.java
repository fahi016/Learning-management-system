package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByUserEmail(String email);
}
