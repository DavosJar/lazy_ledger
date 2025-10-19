package com.lazyledger.backend.moduloLedger.ledger.presentacion.dto;

import com.lazyledger.backend.commons.enums.EstadoLedger;

/**
 * DTO de respuesta para Ledger.
 * Contiene toda la información del ledger para ser devuelta al cliente.
 */
public class LedgerDTO {

    private String id;
    private String nombre;
    private String descripcion;
    private EstadoLedger estado;

    public LedgerDTO() {
    }

    public LedgerDTO(String id, String nombre, String descripcion, EstadoLedger estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoLedger getEstado() {
        return estado;
    }

    public void setEstado(EstadoLedger estado) {
        this.estado = estado;
    }
}
