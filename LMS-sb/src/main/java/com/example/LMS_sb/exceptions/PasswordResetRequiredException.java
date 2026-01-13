package com.example.LMS_sb.exceptions;

public class PasswordResetRequiredException extends RuntimeException{
    public PasswordResetRequiredException(){
        super("PASSWORD_RESET_REQUIRED");
    }
}
