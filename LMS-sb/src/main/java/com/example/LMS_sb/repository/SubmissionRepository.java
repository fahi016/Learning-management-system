package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Submission;
import com.example.LMS_sb.services.SubmissionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findAllByStudentId(Long studentId);
}
