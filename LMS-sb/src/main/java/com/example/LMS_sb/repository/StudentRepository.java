package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
