package com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto;

public class CambiarRolRequest {
    private String clienteId;  // ID del cliente cuyo rol cambiar
    private String ledgerId;  // ID del ledger
    private String nuevoRol;  // Nuevo rol (ASISTENTE, ANALISTA)

    public CambiarRolRequest() {}

    public CambiarRolRequest(String clienteId, String ledgerId, String nuevoRol) {
        this.clienteId = clienteId;
        this.ledgerId = ledgerId;
        this.nuevoRol = nuevoRol;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getNuevoRol() {
        return nuevoRol;
    }

    public void setNuevoRol(String nuevoRol) {
        this.nuevoRol = nuevoRol;
    }
}