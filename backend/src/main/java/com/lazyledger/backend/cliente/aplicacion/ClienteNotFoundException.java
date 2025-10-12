package com.lazyledger.backend.cliente.aplicacion;

public class ClienteNotFoundException extends ClienteApplicationException {
    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}