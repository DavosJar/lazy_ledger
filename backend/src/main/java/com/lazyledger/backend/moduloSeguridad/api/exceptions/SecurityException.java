package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}