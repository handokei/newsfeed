package com.example.newsfeed.exception;

public class UserWrongPasswordException extends RuntimeException {
  public UserWrongPasswordException() {
    super("비밀번호가 다릅니다.");
  }
}
