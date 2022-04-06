package com.epam.esm.gifts.exception;

public class InternalServerException extends RuntimeException{

    private final int ERROR_CODE = 50000;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
