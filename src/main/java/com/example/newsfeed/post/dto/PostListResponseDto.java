package com.example.newsfeed.post.dto;

import com.example.newsfeed.post.Post;

public class PostListResponseDto {

    private Long id;

    private String title;

    private String username;

    private String content;

    private PostListResponseDto(Long id, String title,String username ,String content) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.content = content;
    }

    public static PostListResponseDto listDto(Post post){
        return new PostListResponseDto(post.getId(), post.getTitle(),post.getUser().getUsername(), post.getContent());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
