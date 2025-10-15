package com.lazyledger.backend.miembroLedger.infraestructura;

import com.lazyledger.backend.commons.enums.MiembroRol;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "miembros_ledger")
public class MiembroLedgerEntity {
    
    @EmbeddedId
    private MiembroLedgerId id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "miembro_rol", nullable = false)
    private MiembroRol rol;
    
    @Column(nullable = false)
    private boolean activo;

    public MiembroLedgerEntity() {
    }

    public MiembroLedgerEntity(MiembroLedgerId id, MiembroRol rol, boolean activo) {
        this.id = id;
        this.rol = rol;
        this.activo = activo;
    }

    public MiembroLedgerId getId() {
        return id;
    }

    public void setId(MiembroLedgerId id) {
        this.id = id;
    }

    public UUID getLedgerId() {
        return id.getLedgerId();
    }

    public UUID getClienteId() {
        return id.getClienteId();
    }

    public MiembroRol getRol() {
        return rol;
    }

    public void setRol(MiembroRol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}