package com.example.LMS_sb.dtos;

import lombok.Data;


@Data
public class CreateStudentRequestDto {
    private String name;
    private String email;
    private String password;
    private String rollNumber;
    private String department;

}
