package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends RuntimeException{
    private String msg;

    public UserAlreadyExistsException(String msg) {
        super(String.format("%s",msg));
        this.msg = msg;
    }
}
