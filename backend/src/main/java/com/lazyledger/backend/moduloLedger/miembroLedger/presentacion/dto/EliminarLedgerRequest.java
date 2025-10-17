package com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto;

public class EliminarLedgerRequest {
    private String ledgerId;  // ID del ledger a eliminar

    public EliminarLedgerRequest() {}

    public EliminarLedgerRequest(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }
}