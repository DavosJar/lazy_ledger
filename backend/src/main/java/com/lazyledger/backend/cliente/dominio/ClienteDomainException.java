package com.lazyledger.backend.cliente.dominio;

public class ClienteDomainException extends RuntimeException {
    public ClienteDomainException(String message) {
        super(message);
    }

    public ClienteDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}