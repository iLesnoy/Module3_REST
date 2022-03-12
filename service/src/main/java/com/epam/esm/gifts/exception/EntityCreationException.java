package com.epam.esm.gifts.exception;

public class EntityCreationException extends RuntimeException {
    private final int ERROR_CODE = 40007;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
