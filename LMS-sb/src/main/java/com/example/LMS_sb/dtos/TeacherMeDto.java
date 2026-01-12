package com.example.LMS_sb.dtos;

import com.example.LMS_sb.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherMeDto{
    private String name;
    private String email;
    private Role role;
    private String expertise;
}
