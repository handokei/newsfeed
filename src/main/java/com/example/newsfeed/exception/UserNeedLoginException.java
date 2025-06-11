package com.example.newsfeed.exception;

public class UserNeedLoginException extends RuntimeException {
    public UserNeedLoginException() {
        super("로그인 후 이용해주세요.");
    }
}
