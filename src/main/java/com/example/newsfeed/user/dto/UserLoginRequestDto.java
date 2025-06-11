package com.example.newsfeed.user.dto;

public class UserLoginRequestDto {

    private Long id;

    private String email;

    private String password;


    public UserLoginRequestDto(Long id, String email, String password) {
        this.id = id;
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
