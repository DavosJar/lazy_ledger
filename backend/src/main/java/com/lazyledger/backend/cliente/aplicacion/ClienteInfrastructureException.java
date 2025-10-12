package com.lazyledger.backend.cliente.aplicacion;

public class ClienteInfrastructureException extends ClienteApplicationException {
    public ClienteInfrastructureException(String message) {
        super(message);
    }

    public ClienteInfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}