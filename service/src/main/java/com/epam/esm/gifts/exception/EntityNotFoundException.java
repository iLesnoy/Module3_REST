package com.epam.esm.gifts.exception;

public class EntityNotFoundException extends RuntimeException {
    private final int ERROR_CODE = 40404;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}