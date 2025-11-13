package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class ForbiddenAccessException extends SecurityException {
    public ForbiddenAccessException(String message) {
        super(message);
    }
}