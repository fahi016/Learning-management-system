package com.example.LMS_sb.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(){
        super("Course not found.");
    }
}
