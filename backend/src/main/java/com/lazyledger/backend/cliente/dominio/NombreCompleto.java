package com.lazyledger.backend.cliente.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;

public record NombreCompleto(String nombre, String apellido) {
    public NombreCompleto {
        // Validación del nombre completo
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ValidationException("El apellido no puede ser nulo o vacío");
        }
        nombre = nombre.trim();
        apellido = apellido.trim();
    }

    public static NombreCompleto of(String nombre, String apellido) {
        return new NombreCompleto(nombre, apellido);
    }
    public String getApellido() {
        return apellido;
    }
    public String getNombre() {
        return nombre;
    }
    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}