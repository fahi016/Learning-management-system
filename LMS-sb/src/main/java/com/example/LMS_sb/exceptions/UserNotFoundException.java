package com.example.LMS_sb.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super("USER_NOT_FOUND_WITH_EMAIL: "+email);
    }
}
