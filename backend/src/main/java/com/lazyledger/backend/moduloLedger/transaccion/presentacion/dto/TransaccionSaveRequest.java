package com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto;

public class TransaccionSaveRequest {
    private String ledgerId;
    private Double monto;
    private String tipo;
    private String categoria;
    private String descripcion;
    private String fecha;

    // Constructors
    public TransaccionSaveRequest() {}

    public TransaccionSaveRequest(String ledgerId, Double monto, String tipo, 
                                 String categoria, String descripcion, String fecha) {
        this.ledgerId = ledgerId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters and Setters
    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}