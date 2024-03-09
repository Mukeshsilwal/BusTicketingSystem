package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEntryException extends RuntimeException{

    String msg;

    public DuplicateEntryException(String msg) {
        super(String.format("%s",msg));
        this.msg = msg;
    }
}
