package com.lazyledger.backend.objetivo.presentacion.dto;

public class ObjetivoDto {
    private String id;
    private String nombre;
    private String montoObjetivo;

    public ObjetivoDto() {
    }

    public ObjetivoDto(String id, String nombre, String montoObjetivo) {
        this.id = id;
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMontoObjetivo() {
        return montoObjetivo;
    }

    public void setMontoObjetivo(String montoObjetivo) {
        this.montoObjetivo = montoObjetivo;
    }
}
