package com.example.newsfeed.user.dto;

public class UserLoginRequestDto {

    private String email;

    private String password;


    public UserLoginRequestDto(String email, String password) {
       this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
