package com.lazyledger.backend.moduloLedger.transaccion.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;

public record Descripcion(String valor) {
    public Descripcion {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidationException("La descripción no puede ser nula o vacía");
        }
        if (valor.length() > 255) {
            throw new ValidationException("La descripción no puede exceder 255 caracteres");
        }
        valor = valor.trim();
    }

    public static Descripcion of(String valor) {
        return new Descripcion(valor);
    }
}