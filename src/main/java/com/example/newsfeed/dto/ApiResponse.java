package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private int status;

    private String message;

    private T date;

    public ApiResponse(int status, String message, T date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
