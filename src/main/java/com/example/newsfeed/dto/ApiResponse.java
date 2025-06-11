package com.example.newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //null인 필드 제외 어노테이션
public class ApiResponse<T> {
    private int status;

    private String message;

    private T data;

    //error시 사용
    private String path;

    //Jpa가 사용
    public ApiResponse() {
    }

    //기본적으로 사용
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    //실패시 사용
    public ApiResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
