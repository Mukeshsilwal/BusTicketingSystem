package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatsNotAvailableException extends RuntimeException{
    private String msg;
    public SeatsNotAvailableException(String msg) {
        super(String.format("%s Not Available",msg));
        this.msg=msg;
    }
}
