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

    /**
     * Constructor privado para forzar el uso de métodos factory con nombres explícitos.
     * Esto previene errores al intercambiar los parámetros UUID.
     */
    private MiembroLedgerId(UUID ledgerId, UUID clienteId) {
        this.ledgerId = ledgerId;
        this.clienteId = clienteId;
    }

    /**
     * Método factory con parámetros nombrados explícitamente.
     * Uso: MiembroLedgerId.of(ledgerId, clienteId)
     */
    public static MiembroLedgerId of(UUID ledgerId, UUID clienteId) {
        if (ledgerId == null) throw new IllegalArgumentException("ledgerId no puede ser null");
        if (clienteId == null) throw new IllegalArgumentException("clienteId no puede ser null");
        return new MiembroLedgerId(ledgerId, clienteId);
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