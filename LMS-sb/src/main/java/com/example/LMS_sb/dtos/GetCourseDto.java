package com.example.LMS_sb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseDto {
    private String title;
    private String description;
    private String teacherName;
    private LocalDateTime joinedAt;
}
