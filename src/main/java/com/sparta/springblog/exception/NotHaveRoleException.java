package com.sparta.springblog.exception;

public class NotHaveRoleException extends RuntimeException{

    public NotHaveRoleException(String message) {
        super(message);
    }
}
