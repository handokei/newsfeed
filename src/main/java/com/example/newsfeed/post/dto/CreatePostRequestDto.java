package com.example.newsfeed.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostRequestDto {

    @NotNull(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "제목은 30자 이내로 작성하세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요")
    @Size(min = 4, max = 300, message = "내용은 4자 이상 300자 이내로만 작성 하세요." )
    private String content;

    public CreatePostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
