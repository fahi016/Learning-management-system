package com.example.LMS_sb.exceptions;

public class SubmissionNotFoundException extends RuntimeException{
    public SubmissionNotFoundException(){
        super("Submission not found");
    }
}
