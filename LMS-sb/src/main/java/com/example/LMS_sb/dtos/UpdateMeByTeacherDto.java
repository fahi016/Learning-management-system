package com.example.LMS_sb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMeByTeacherDto {
    private String name;
    private String expertise;
}
