package com.lazyledger.backend.ledger.dominio;

import com.lazyledger.backend.commons.identificadores.LedgerId;

public class Ledger {
    private final LedgerId id;
    private final String nombre;
    private final String descripcion;

    public Ledger(LedgerId id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
