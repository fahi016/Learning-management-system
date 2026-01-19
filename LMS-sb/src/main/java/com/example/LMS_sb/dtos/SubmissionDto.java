package com.example.LMS_sb.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {
    private Long submissionId;
    private Long assignmentId;
    private String assignmentTitle;
    private Long courseId;
    private String courseTitle;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private Integer grade;
    private String feedback;
    private boolean graded;

}
