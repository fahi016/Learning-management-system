package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.SubmissionDto;
import com.example.LMS_sb.exceptions.SubmissionNotFoundException;
import com.example.LMS_sb.models.Submission;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.SubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {
    StudentService studentService;
    SubmissionRepository submissionRepository;

    public List<SubmissionDto> getMySubmissions(Authentication authentication){
         Long studentId = studentService.getStudentByUserEmail(authentication.getName()).getId();
         List<Submission> submissions = submissionRepository.findAllByStudentId(studentId);
         return submissions.stream().map(
                 submission -> new SubmissionDto(
                         submission.getId(),
                         submission.getAssignment().getId(),
                         submission.getAssignment().getTitle(),
                         submission.getAssignment().getCourse().getId(),
                         submission.getAssignment().getCourse().getTitle(),
                         submission.getFileUrl(),
                         submission.getSubmittedAt(),
                         submission.getGrade(),
                         submission.getFeedback(),
                         submission.getGrade()!=null

                         )
         ).toList();
    }
}
