package com.example.newsfeed.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    private final String email;

    public EmailAlreadyExistsException(String email){
        super("이미 존재하는 이메일입니다. ");
        this.email= email;
    }
}
