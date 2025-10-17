package com.lazyledger.backend.moduloLedger.transaccion.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import java.time.LocalDateTime;

public record Fecha(LocalDateTime valor) {
    public Fecha {
        if (valor == null) {
            throw new ValidationException("La fecha no puede ser nula");
        }
        if (valor.isAfter(LocalDateTime.now())) {
            throw new ValidationException("La fecha no puede ser en el futuro");
        }
    }

    public static Fecha of(LocalDateTime valor) {
        return new Fecha(valor);
    }
}