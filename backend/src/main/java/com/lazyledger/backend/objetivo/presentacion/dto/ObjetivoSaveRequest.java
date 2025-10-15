package com.lazyledger.backend.objetivo.presentacion.dto;

public class ObjetivoSaveRequest {
    private String nombre;
    private double montoObjetivo;

    public ObjetivoSaveRequest() {
    }

    public ObjetivoSaveRequest(String nombre, double montoObjetivo) {
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMontoObjetivo() {
        return montoObjetivo;
    }

    public void setMontoObjetivo(double montoObjetivo) {
        this.montoObjetivo = montoObjetivo;
    }
}
