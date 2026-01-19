package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.SubmissionCreateRequestDto;
import com.example.LMS_sb.dtos.SubmissionDto;
import com.example.LMS_sb.dtos.SubmissionResponseDto;
import com.example.LMS_sb.exceptions.DuplicateSubmissionException;
import com.example.LMS_sb.exceptions.SubmissionNotFoundException;
import com.example.LMS_sb.models.Assignment;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Submission;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.SubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {
    StudentService studentService;
    SubmissionRepository submissionRepository;
    AssignmentService assignmentService;
    EnrollmentService enrollmentService;

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

    public SubmissionResponseDto submitAssignment(Long assignmentId,SubmissionCreateRequestDto dto,Authentication authentication) {
        Student student = studentService.getStudentByUserEmail(authentication.getName());
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        boolean isEnrolled = enrollmentService.isStudentEnrolled(student.getId(),assignment.getCourse().getId());

        if (!isEnrolled) {
            throw new AccessDeniedException(
                    "Student is not enrolled in this course"
            );
        }

        boolean alreadySubmitted = submissionRepository.existsByAssignmentIdAndStudentId(assignment.getId(), student.getId());
        if(alreadySubmitted){
            throw new DuplicateSubmissionException(
                    "Assignment already submitted"
            );
        }
        boolean isLate = assignment.getDueDate()!=null && LocalDateTime.now().isAfter(assignment.getDueDate());
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setFileUrl(dto.getFileUrl());
        submission.setLateSubmission(isLate);
        submissionRepository.save(submission);

        return new SubmissionResponseDto(
                submission.getId(),
                assignment.getTitle(),
                submission.getSubmittedAt(),
                false,
                isLate
        );
    }
}
