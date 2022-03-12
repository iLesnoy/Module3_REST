package com.epam.esm.gifts.exception;

public class EntityTagNameValidationException extends RuntimeException {
    private static final int ERROR_CODE = 40006;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}