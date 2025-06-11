package com.example.newsfeed.post.dto;

import com.example.newsfeed.post.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;

    private String title;

    private String username;

    private String content;

    public PostResponseDto(Long id, String title, String username, String content) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.content = content;
    }

    //Jpa가 사용예정
    public PostResponseDto() {

    }
    public static PostResponseDto from(Post post){
        return new PostResponseDto(post.getId(), post.getTitle(),post.getUser().getUsername(),post.getContent());
    }
}
