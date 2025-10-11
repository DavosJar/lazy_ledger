package com.lazyledger.backend.cliente.dominio;


public record Direccion(String calle, String ciudad, String pais, String codigoPostal) {
    public Direccion {
        // Validación de la dirección
        if (calle == null || calle.trim().isEmpty()) {
            throw new IllegalArgumentException("La calle no puede ser nula o vacía");
        }
        if (ciudad == null || ciudad.trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede ser nula o vacía");
        }
        if (pais == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException("El país no puede ser nulo o vacío");
        }
        calle = calle.trim();
        ciudad = ciudad.trim();
        pais = pais.trim();
        if (codigoPostal != null) {
            codigoPostal = codigoPostal.trim();
        }
    }

    public static Direccion of(String calle, String ciudad, String pais, String codigoPostal) {
        return new Direccion(calle, ciudad, pais, codigoPostal);
    }
    @Override
    public String toString() {
        return calle + ", " + ciudad + ", " + pais + (codigoPostal != null ? " " + codigoPostal : "");
    }
}