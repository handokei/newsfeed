package com.example.newsfeed.user.dto;

public class UserResponseDto {

    private String username;

    public UserResponseDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
