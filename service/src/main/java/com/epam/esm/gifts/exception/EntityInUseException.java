package com.epam.esm.gifts.exception;

public class EntityInUseException extends RuntimeException{

    private final int ERROR_CODE = 40902;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
