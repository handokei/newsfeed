package com.example.newsfeed.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;

    private LocalDateTime timestamp;

    private String path;

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
