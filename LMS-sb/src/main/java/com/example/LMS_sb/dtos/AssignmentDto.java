package com.example.LMS_sb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {
    private Long id;
    private String title;
    private String description;
    private String courseId;
    private LocalDateTime dueDate;
}
