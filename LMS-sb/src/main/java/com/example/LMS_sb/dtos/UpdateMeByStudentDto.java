package com.example.LMS_sb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMeByStudentDto {
    private String name;
    private String rollNumber;
    private String department;
}
