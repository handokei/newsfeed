package com.example.newsfeed.user.dto;

public class UserLoginResponseDto {
    private String username;
    private String sessionId;

    public UserLoginResponseDto(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
