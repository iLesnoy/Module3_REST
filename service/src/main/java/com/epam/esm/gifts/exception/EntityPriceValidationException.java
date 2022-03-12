package com.epam.esm.gifts.exception;

public class EntityPriceValidationException extends RuntimeException {
    private final int ERROR_CODE = 40003;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}