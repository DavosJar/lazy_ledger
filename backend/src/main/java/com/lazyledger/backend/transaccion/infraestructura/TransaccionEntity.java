package com.lazyledger.backend.transaccion.infraestructura;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;

@Entity
@Table(name = "transacciones")
public class TransaccionEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "ledger_id", nullable = false, columnDefinition = "UUID")
    private UUID ledgerId;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTransaccion tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // Constructors
    public TransaccionEntity() {}

    public TransaccionEntity(UUID id, UUID ledgerId, Double monto, TipoTransaccion tipo, 
                            Categoria categoria, String descripcion, LocalDateTime fecha) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(UUID ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}