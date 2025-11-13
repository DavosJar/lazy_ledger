package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class InvalidTokenException extends SecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }
}