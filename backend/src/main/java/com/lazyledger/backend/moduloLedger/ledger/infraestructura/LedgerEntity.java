package com.lazyledger.backend.moduloLedger.ledger.infraestructura;

import jakarta.persistence.*;
import java.util.UUID;
import com.lazyledger.backend.commons.enums.EstadoLedger;

@Entity
@Table(name = "ledgers")
public class LedgerEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoLedger estado;

    public LedgerEntity() {}

    public LedgerEntity(UUID id, String nombre, String descripcion, EstadoLedger estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoLedger getEstado() { return estado; }
    public void setEstado(EstadoLedger estado) { this.estado = estado; }
}
