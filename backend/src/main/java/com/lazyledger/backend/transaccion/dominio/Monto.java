
package com.lazyledger.backend.transaccion.dominio;

import java.math.BigDecimal;

public record Monto(BigDecimal valor) {

    public Monto {
        validarMonto(valor);
    }

    public static Monto of(BigDecimal valor) {
        return new Monto(valor);
    }

    private static void validarMonto(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (valor.scale() > 2) {
            throw new IllegalArgumentException("El monto no puede tener más de 2 decimales");
        }
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}