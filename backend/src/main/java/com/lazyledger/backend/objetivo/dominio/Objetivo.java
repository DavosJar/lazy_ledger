package com.lazyledger.backend.objetivo.dominio;

import com.lazyledger.backend.commons.identificadores.ObjetivoId;

public class Objetivo {
    private final ObjetivoId id;
    private final String nombre;
    private final double montoObjetivo;

    public Objetivo(ObjetivoId id, String nombre, double montoObjetivo) {
        this.id = id;
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
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
