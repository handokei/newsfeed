package com.example.newsfeed.post.dto;

public class PostRequestDto {
    private String username;

    private String title;

    private String content;

    public PostRequestDto(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }


    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
