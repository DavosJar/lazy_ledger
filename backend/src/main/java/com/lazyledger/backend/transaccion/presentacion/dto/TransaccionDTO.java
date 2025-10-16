package com.lazyledger.backend.transaccion.presentacion.dto;

public class TransaccionDTO {
    // Atributos del DTO
    private String id;
    private String ledgerId;
    private Double monto;
    private String tipo;
    private String categoria;
    private String descripcion;
    private String fecha;

    public TransaccionDTO() {
    }

    public TransaccionDTO(String id, String ledgerId, Double monto, String tipo, 
                         String categoria, String descripcion, String fecha) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

