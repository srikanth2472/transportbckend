package com.example.transport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
    	ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    
}
