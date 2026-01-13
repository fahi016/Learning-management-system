package com.example.LMS_sb.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("INVALID_CREDENTIALS");
    }
}
