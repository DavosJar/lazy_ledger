package com.lazyledger.backend.commons.exceptions;

public class InfrastructureException extends ApplicationException {
    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}