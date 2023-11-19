package com.Transaction.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> createException(ResourceNotFoundException e){
        String msg=e.getMessage();
        ApiResponse apiResponse=new ApiResponse(msg,true, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> response(ResourceNotFound e){
        String msg=e.getMessage();
        ApiResponse response=new ApiResponse(msg,true,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
