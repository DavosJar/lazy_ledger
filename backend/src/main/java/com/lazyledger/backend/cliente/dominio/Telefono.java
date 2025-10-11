package com.lazyledger.backend.cliente.dominio;

public record Telefono(String numero) {
    public Telefono {
        // Validación del número de teléfono
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede ser nulo o vacío");
        }
        numero = numero.trim();
        // Aquí se podría agregar validación de formato más compleja
    }

    public static Telefono of(String numero) {
        return new Telefono(numero);
    }

    @Override
    public String toString() {
        return numero;
    }
}