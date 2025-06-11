package com.example.newsfeed.exception;

import com.example.newsfeed.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyException(EmailAlreadyExistsException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getServletPath());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getServletPath());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler(LoginUserPasswordException.class)
    public ResponseEntity<ErrorResponse> handleLoginUserPasswordException(LoginUserPasswordException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getServletPath());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

    }
}
