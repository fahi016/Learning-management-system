package com.example.LMS_sb.dtos;


import lombok.Data;

@Data
public class AddUpdateLectureDto {
    private String title;
    private String videoUrl;
    private String notesUrl;
}
