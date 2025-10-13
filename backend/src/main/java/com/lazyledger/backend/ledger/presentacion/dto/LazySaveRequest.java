package com.lazyledger.backend.ledger.presentacion.dto;

public class LazySaveRequest {
    private String nombre;
    private String descripcion;

    //constructores
    public LazySaveRequest() {
    }

    public LazySaveRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //getter y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
