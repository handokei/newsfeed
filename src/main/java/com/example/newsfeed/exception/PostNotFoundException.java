package com.example.newsfeed.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("게시물을 찾을 수 없습니다.");
    }
}
