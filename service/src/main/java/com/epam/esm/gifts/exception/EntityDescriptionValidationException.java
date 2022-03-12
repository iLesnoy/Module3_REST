package com.epam.esm.gifts.exception;

public class EntityDescriptionValidationException extends RuntimeException {

    private final int ERROR_CODE = 40002;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}