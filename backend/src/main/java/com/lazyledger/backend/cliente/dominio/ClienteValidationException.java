package com.lazyledger.backend.cliente.dominio;

public class ClienteValidationException extends ClienteDomainException {
    public ClienteValidationException(String message) {
        super(message);
    }

    public ClienteValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}