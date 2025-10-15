package com.lazyledger.backend.miembroLedger.presentacion.dto;

public class MiembroLedgerDTO {
    private String clienteId;
    private String ledgerId;
    private String rol;
    private boolean activo;

    public MiembroLedgerDTO() {}

    public MiembroLedgerDTO(String clienteId, String ledgerId, String rol, boolean activo) {
        this.clienteId = clienteId;
        this.ledgerId = ledgerId;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters and Setters
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}