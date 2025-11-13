package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class OwnerRequiredException extends SecurityException {
    public OwnerRequiredException(String message) {
        super(message);
    }
}