package com.example.LMS_sb.dtos;


import lombok.Data;

@Data
public class AddLectureDto {
    private String title;
    private String videoUrl;
    private String notesUrl;
}
