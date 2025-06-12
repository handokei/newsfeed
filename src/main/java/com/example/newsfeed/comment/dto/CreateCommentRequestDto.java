package com.example.newsfeed.comment.dto;

import com.example.newsfeed.post.Post;
import com.example.newsfeed.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCommentRequestDto {

    @NotNull(message = "내용을 입력하세요.")
    @Size(max = 255, message = "255자 이내로 입력하세요.")
    private String content;

    //Jpa가 사용예정
    public CreateCommentRequestDto() {
    }
    protected CreateCommentRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
