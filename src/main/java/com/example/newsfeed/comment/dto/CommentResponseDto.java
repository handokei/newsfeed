package com.example.newsfeed.comment.dto;

import com.example.newsfeed.comment.Comment;

public class CommentResponseDto {

    private Long id;

    private String username;

    private String content;

    public CommentResponseDto(Long id, String username, String content) {
        this.id = id;
        this.username = username;
        this.content = content;
    }

    public static CommentResponseDto from(Comment comment) {

        return new CommentResponseDto(comment.getId(),comment.getUser().getUsername(), comment.getContent());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
