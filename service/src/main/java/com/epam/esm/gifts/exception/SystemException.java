package com.epam.esm.gifts.exception;



public class SystemException extends RuntimeException{

    int ErrorCode;

    public SystemException(int errorCode){
        this.ErrorCode = errorCode;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }
}
