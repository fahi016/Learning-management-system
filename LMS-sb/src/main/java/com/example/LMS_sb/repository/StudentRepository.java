package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUserEmail(String email);
}
