package com.lazyledger.backend.moduloLedger.ledger.presentacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO para crear un nuevo Ledger.
 * El ledger se crea en estado ACTIVO por defecto.
 * El cliente que crea el ledger automáticamente se convierte en PROPIETARIO.
 */
public class LedgerSaveRequest {

    @NotBlank(message = "El nombre del ledger no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    public LedgerSaveRequest() {
    }

    public LedgerSaveRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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
}
