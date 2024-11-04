package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingNotFoundException extends RuntimeException {

    private String msg;

    public BookingNotFoundException(String msg) {
        super(String.format("%s", msg));
        this.msg = msg;
    }
}
