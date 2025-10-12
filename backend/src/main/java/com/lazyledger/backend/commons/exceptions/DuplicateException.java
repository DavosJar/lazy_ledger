package com.lazyledger.backend.commons.exceptions;

public class DuplicateException extends ApplicationException {
    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}