package com.example.LMS_sb.dtos;

import lombok.Data;


@Data
public class CreateTeacherRequestDto {
    private String name;
    private String email;
    private String password;
    private String expertise;

}
