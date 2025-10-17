package com.lazyledger.backend.moduloSeguridad.api.exceptions;

import com.lazyledger.backend.commons.exceptions.DomainException;

public class AutenticacionException extends DomainException {
    public AutenticacionException(String message) {
        super(message);
    }

    public AutenticacionException(String message, Throwable cause) {
        super(message, cause);
    }
}