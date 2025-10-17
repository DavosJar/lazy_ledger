package com.lazyledger.backend.moduloLedger.deuda.presentacion.dto;

public class DeudaSaveRequest {
    public String nombre;
    public Double montoTotal;
    public String descripcion;
    public String fechaCreacion;
    public String fechaVencimiento;
    public String estado;

    //constructores
    public DeudaSaveRequest() {
        }
    
    public DeudaSaveRequest(String nombre, Double montoTotal, String descripcion, String fechaCreacion, String fechaVencimiento, String estado) {
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    //getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
