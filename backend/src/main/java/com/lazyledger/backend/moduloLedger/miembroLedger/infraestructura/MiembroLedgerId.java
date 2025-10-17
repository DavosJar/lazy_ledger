package com.lazyledger.backend.moduloLedger.miembroLedger.infraestructura;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MiembroLedgerId implements Serializable {
    
    @Column(name = "ledger_id")
    private UUID ledgerId;
    
    @Column(name = "cliente_id")
    private UUID clienteId;

    public MiembroLedgerId() {
    }

    public MiembroLedgerId(UUID ledgerId, UUID clienteId) {
        this.ledgerId = ledgerId;
        this.clienteId = clienteId;
    }

    public UUID getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(UUID ledgerId) {
        this.ledgerId = ledgerId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MiembroLedgerId)) return false;
        MiembroLedgerId that = (MiembroLedgerId) o;
        return Objects.equals(ledgerId, that.ledgerId) && 
               Objects.equals(clienteId, that.clienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledgerId, clienteId);
    }
}