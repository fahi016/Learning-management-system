package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
