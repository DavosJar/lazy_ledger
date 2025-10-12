package com.lazyledger.backend.cliente.dominio;

public class ClienteBusinessException extends ClienteDomainException {
    public ClienteBusinessException(String message) {
        super(message);
    }

    public ClienteBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}