package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class UnauthorizedAccessException extends SecurityException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}