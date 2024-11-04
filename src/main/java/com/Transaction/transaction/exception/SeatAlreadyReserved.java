package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAlreadyReserved extends RuntimeException {
    private String msg;

    public SeatAlreadyReserved(String msg) {
        super(String.format("%s", msg));
        this.msg = msg;
    }
}
