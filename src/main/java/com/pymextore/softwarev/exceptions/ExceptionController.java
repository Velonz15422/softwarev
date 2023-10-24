package com.pymextore.softwarev.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<String> handleCustomException(CustomException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = AuthFailException.class)
    public final  ResponseEntity<String> handleAuthFailException(AuthFailException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
