package com.example.newsfeed.user.dto;

public class UserLoginResponseDto {

    private Long id;
    private String username;
    private String token;

    public UserLoginResponseDto(Long id, String username, String sessionId) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return token;
    }
}
