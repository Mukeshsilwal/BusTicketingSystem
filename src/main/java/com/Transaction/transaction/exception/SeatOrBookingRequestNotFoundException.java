package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatOrBookingRequestNotFoundException extends RuntimeException {
    private String msg;

    public SeatOrBookingRequestNotFoundException(String msg) {
        super(String.format("%s", msg));
        this.msg = msg;
    }
}
