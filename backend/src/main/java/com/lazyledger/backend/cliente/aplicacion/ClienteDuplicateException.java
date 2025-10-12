package com.lazyledger.backend.cliente.aplicacion;

public class ClienteDuplicateException extends ClienteApplicationException {
    public ClienteDuplicateException(String message) {
        super(message);
    }

    public ClienteDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}