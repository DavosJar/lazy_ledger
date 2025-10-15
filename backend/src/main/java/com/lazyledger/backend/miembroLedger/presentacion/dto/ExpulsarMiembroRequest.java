package com.lazyledger.backend.miembroLedger.presentacion.dto;

public class ExpulsarMiembroRequest {
    private String clienteId;  // ID del cliente a expulsar
    private String ledgerId;  // ID del ledger

    public ExpulsarMiembroRequest() {}

    public ExpulsarMiembroRequest(String clienteId, String ledgerId) {
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