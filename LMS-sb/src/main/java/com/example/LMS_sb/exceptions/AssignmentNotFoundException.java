package com.example.LMS_sb.exceptions;

public class AssignmentNotFoundException extends RuntimeException{
    public AssignmentNotFoundException(){
        super("Assignment not found");
    }
}
