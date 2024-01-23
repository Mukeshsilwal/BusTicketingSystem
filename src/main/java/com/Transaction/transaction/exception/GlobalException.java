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
    @ExceptionHandler(SeatsNotAvailableException.class)
    public ResponseEntity<ApiResponse> responseEntity(SeatsNotAvailableException e){
        String message=e.getMessage();
        ApiResponse response=new ApiResponse(message,false,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SeatAlreadyReserved.class)
    public ResponseEntity<ApiResponse> response(SeatAlreadyReserved e){
        String message=e.getMessage();
        ApiResponse response=new ApiResponse(message,false,HttpStatus.IM_USED);
        return new ResponseEntity<>(response,HttpStatus.IM_USED);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> UserNotFound(UserAlreadyExistsException e){
        String message=e.getMessage();
        ApiResponse response=new ApiResponse(message,true,HttpStatus.IM_USED);
        return new ResponseEntity<>(response,HttpStatus.IM_USED);
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ApiResponse> PasswordIncorrect(PasswordIncorrectException e){
        String message=e.getMessage();
        ApiResponse response=new ApiResponse(message,true,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ApiResponse>  BookingExc(BookingNotFoundException e){
        String message=e.getMessage();
        ApiResponse response=new ApiResponse(message,false,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
