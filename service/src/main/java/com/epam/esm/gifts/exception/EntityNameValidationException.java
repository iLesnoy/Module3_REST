package com.epam.esm.gifts.exception;

public class EntityNameValidationException extends RuntimeException {
    private final int ERROR_CODE = 40001;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
