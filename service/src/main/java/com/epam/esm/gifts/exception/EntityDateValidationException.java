package com.epam.esm.gifts.exception;

public class EntityDateValidationException extends RuntimeException {
    private final int ERROR_CODE = 40005;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}