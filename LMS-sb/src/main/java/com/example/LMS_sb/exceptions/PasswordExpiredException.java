package com.example.LMS_sb.exceptions;

public class PasswordExpiredException extends RuntimeException{
    public PasswordExpiredException(){
        super("PASSWORD_EXPIRED");
    }
}
