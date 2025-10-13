package com.lazyledger.backend.transaccion.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import java.math.BigDecimal;

public record Monto(BigDecimal valor) {
    public Monto {
        if (valor == null) {
            throw new ValidationException("El monto no puede ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El monto debe ser mayor a cero");
        }
    }

    public static Monto of(BigDecimal valor) {
        return new Monto(valor);
    }
}