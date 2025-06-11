package com.example.newsfeed.exception;

public class PostNotUserPostException extends RuntimeException {
    public PostNotUserPostException() {
        super("본인의 게시글이 아닙니다.");
    }
}
