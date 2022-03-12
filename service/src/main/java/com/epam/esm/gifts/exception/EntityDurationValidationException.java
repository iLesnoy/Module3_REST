package com.epam.esm.gifts.exception;

public class EntityDurationValidationException extends RuntimeException {
    private final int ERROR_CODE = 40004;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
