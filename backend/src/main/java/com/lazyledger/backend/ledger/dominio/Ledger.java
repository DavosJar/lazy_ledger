package com.lazyledger.backend.ledger.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.LedgerId;

public class Ledger {
    private final LedgerId id;
    private final String nombre;
    private final String descripcion;

    private Ledger(LedgerId id, String nombre, String descripcion) {
        if (id == null) {
            throw new ValidationException("El ID del ledger no puede ser nulo");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre del ledger no puede ser nulo o vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ValidationException("La descripción del ledger no puede ser nula o vacía");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public static Ledger create(LedgerId id, String nombre, String descripcion) {
        return new Ledger(id, nombre, descripcion);
    }

    public static Ledger rehydrate(LedgerId id, String nombre, String descripcion) {
        return new Ledger(id, nombre, descripcion);
    }

    public LedgerId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static void main(String[] args) {
        LedgerId ledgerId = LedgerId.of(java.util.UUID.randomUUID());
        Ledger ledger = new Ledger(ledgerId, "Ledger Ejemplo", "Descripción del Ledger Ejemplo");
        System.out.println("Ledger ID: " + ledger.getId());
        System.out.println("Nombre: " + ledger.getNombre());
        System.out.println("Descripción: " + ledger.getDescripcion());
    }
}
