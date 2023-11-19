package com.Transaction.transaction.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private int fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
        super(String.format("%s NOT FOUND WITH %s:%s",resourceName,fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
