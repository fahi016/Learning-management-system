package com.example.LMS_sb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetAllSubmissionByTeacherDto {

    private Long submissionId;
    private Long studentId;
    private String studentName;
    private String assignmentTitle;
    private Long courseId;
    private String courseTitle;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private Integer grade;
    private String feedback;
    private boolean graded;
}
