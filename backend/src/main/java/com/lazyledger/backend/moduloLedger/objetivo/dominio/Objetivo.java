package com.lazyledger.backend.moduloLedger.objetivo.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.ObjetivoId;

public class Objetivo {
    private final ObjetivoId id;
    private final String nombre;
    private final double montoObjetivo;

    public Objetivo(ObjetivoId id, String nombre, double montoObjetivo) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede ser nulo o vacío");
        }
        if (montoObjetivo <= 0) {
            throw new ValidationException("El monto objetivo debe ser mayor que cero");
        }
        this.id = id;
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
    }

    public static Objetivo create(ObjetivoId id, String nombre, double montoObjetivo) {
        return new Objetivo(id, nombre, montoObjetivo);
    }

    public static Objetivo rehydrate(ObjetivoId id, String nombre, double montoObjetivo) {
        return new Objetivo(id, nombre, montoObjetivo);
    }

    //getters
    public ObjetivoId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMontoObjetivo() {
        return montoObjetivo;
    }

    @Override
    public String toString() {
        return "Objetivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", montoObjetivo=" + montoObjetivo +
                '}';
    }

    public static void main(String[] args) {
        ObjetivoId objetivoId = ObjetivoId.of(java.util.UUID.randomUUID());
        Objetivo objetivo = new Objetivo(objetivoId, "Comprar una casa", 250000.0);
        System.out.println(objetivo);
    }
}
