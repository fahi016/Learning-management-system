package com.example.LMS_sb.exceptions;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(){
        super("ACCOUNT_LOCKED_CONTACT_ADMIN");
    }
}
