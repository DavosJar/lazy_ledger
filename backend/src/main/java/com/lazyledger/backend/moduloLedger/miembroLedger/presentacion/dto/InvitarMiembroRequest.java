package com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto;

public class InvitarMiembroRequest {
    private String clienteId;  // ID del cliente a invitar
    private String ledgerId;  // ID del ledger donde invitar

    public InvitarMiembroRequest() {}

    public InvitarMiembroRequest(String clienteId, String ledgerId) {
        this.clienteId = clienteId;
        this.ledgerId = ledgerId;
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
}