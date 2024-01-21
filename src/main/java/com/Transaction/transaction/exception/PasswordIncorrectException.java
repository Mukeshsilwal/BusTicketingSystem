package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordIncorrectException extends RuntimeException{
    private String msg;

    public PasswordIncorrectException(String msg) {
        super(String.format("%s",msg));
        this.msg = msg;
    }
}
