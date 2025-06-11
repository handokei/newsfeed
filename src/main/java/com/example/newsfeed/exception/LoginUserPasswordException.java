package com.example.newsfeed.exception;

public class LoginUserPasswordException extends RuntimeException {
    public LoginUserPasswordException() {
        super("비밀 번호가 일치하지 않습니다.");
    }
}
