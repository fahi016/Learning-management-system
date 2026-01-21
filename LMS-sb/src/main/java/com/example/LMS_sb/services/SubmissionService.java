package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.exceptions.AssignmentNotFoundException;
import com.example.LMS_sb.exceptions.DuplicateSubmissionException;
import com.example.LMS_sb.exceptions.SubmissionNotFoundException;
import com.example.LMS_sb.models.*;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.SubmissionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {
    private StudentService studentService;
    private SubmissionRepository submissionRepository;
    private AssignmentService assignmentService;
    private EnrollmentService enrollmentService;
    private TeacherService teacherService;

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
        submission.setFeedback(dto.getFeedback());
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

    @Transactional()
    public List<GetAllSubmissionByTeacherDto> viewSubmissionsForTeacher(Authentication authentication, Long assignmentId) {
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        List<Submission> submissions = submissionRepository.findAllByAssignment(assignment);
        if(!assignment.getCourse().getTeacher().getId().equals(teacher.getId())){
            throw new AccessDeniedException(
                    "Teacher does not own this assignment"
            );        }
        return submissions.stream().map(
                submission -> new GetAllSubmissionByTeacherDto(
                        submission.getId(),
                        submission.getStudent().getId(),
                        submission.getStudent().getUser().getName(),
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
    @Transactional
    public void gradeSubmission(Authentication authentication, Long submissionId, GradeDto dto) {
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(SubmissionNotFoundException::new);
        if(!submission.getAssignment().getCourse().getTeacher().getId().equals(teacher.getId())){
            throw new AccessDeniedException(
                    "Teacher does not own this assignment"
            );
        }

        if (submission.getGrade() != null) {
            throw new IllegalStateException(
                    "Submission has already been graded"
            );
        }

        if (dto.getGrade() < 0 || dto.getGrade() > 100) {
            throw new IllegalArgumentException(
                    "Grade must be between 0 and 100"
            );
        }
        submission.setGrade(dto.getGrade());
        submissionRepository.save(submission);

    }
}
