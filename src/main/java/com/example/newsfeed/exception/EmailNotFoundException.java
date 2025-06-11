package com.example.newsfeed.exception;

public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException() {
    super("이메일이 존재하지 않습니다.");
  }


}
