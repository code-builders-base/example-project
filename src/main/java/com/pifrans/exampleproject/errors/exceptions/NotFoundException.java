package com.pifrans.exampleproject.errors.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable throwable) {
        super(throwable);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
