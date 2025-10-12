package com.lazyledger.backend.cliente.aplicacion;

public class ClienteApplicationException extends RuntimeException {
    public ClienteApplicationException(String message) {
        super(message);
    }

    public ClienteApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}