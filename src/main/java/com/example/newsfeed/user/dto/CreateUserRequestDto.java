package com.example.newsfeed.user.dto;

public class CreateUserRequestDto {

    private String username;

    private String email;

    private String password;

    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public CreateUserRequestDto() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
