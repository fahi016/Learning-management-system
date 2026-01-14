package com.example.LMS_sb.dtos;

import com.example.LMS_sb.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    private String name;
    private String email;
    private String expertise;
}
