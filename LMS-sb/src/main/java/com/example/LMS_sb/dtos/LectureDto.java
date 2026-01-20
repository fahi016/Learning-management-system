package com.example.LMS_sb.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LectureDto {
    private Long id;
    private String title;
    private String videoUrl;
    private String notesUrl;
}
