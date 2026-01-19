package com.example.LMS_sb.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SubmissionResponseDto {

    private Long submissionId;
    private String assignmentTitle;
    private LocalDateTime submittedAt;
    private boolean graded;
    private boolean lateSubmission;
}
