package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private String Message;
    private boolean status;
    private HttpStatus httpStatus;

    public ApiResponse(String message, boolean status, HttpStatus httpStatus) {
        Message = message;
        this.status = status;
        this.httpStatus = httpStatus;
    }
}
