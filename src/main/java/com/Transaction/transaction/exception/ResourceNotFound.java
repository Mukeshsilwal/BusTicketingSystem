package com.Transaction.transaction.exception;

public class ResourceNotFound extends RuntimeException{
    private String message;

    public ResourceNotFound(String message) {
        super(message);
        this.message = message;
    }
}
