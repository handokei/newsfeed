package com.example.newsfeed.exception;

import com.example.newsfeed.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyException(EmailAlreadyExistsException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmailNotFoundException(EmailNotFoundException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(LoginUserPasswordException.class)
    public ResponseEntity<ApiResponse> handleLoginUserPasswordException(LoginUserPasswordException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserNeedLoginException.class)
    public ResponseEntity<ApiResponse> handleNeedUserLoginException(UserNeedLoginException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse> handlePostNotFoundException(PostNotFoundException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PostNotUserPostException.class)
    public ResponseEntity<ApiResponse> handlePostNotUserPostException(PostNotUserPostException ex, HttpServletRequest request){
        ApiResponse errorResponse = new ApiResponse(401, ex.getMessage(), request.getServletPath() );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
