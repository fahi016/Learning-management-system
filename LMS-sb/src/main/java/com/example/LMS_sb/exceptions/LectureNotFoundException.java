package com.example.LMS_sb.exceptions;

public class LectureNotFoundException extends RuntimeException{
    public LectureNotFoundException(){
        super("Lecture not found");
    }
}
