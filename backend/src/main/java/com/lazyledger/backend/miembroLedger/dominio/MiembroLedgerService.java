package com.lazyledger.backend.miembroLedger.dominio;

import java.util.Objects;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.exceptions.InvalidStateException;

public class MiembroLedgerService {

    public MiembroLedger cambiarRol(MiembroLedger miembro, MiembroLedger solicitante, MiembroRol nuevoRol) {
        validarAutorizacion(miembro, solicitante, "cambiar roles");
        return miembro.cambiarRol(nuevoRol);
    }

    public MiembroLedger hacerAsistente(MiembroLedger miembro, MiembroLedger solicitante) {
        return cambiarRol(miembro, solicitante, MiembroRol.ASISTENTE);
    }

    public MiembroLedger hacerAnalista(MiembroLedger miembro, MiembroLedger solicitante) {
        return cambiarRol(miembro, solicitante, MiembroRol.ANALISTA);
    }

    public MiembroLedger desactivar(MiembroLedger miembro, MiembroLedger solicitante) {
        validarAutorizacion(miembro, solicitante, "desactivar miembros");
        return miembro.desactivar();
    }

    public MiembroLedger activar(MiembroLedger miembro, MiembroLedger solicitante) {
        validarAutorizacion(miembro, solicitante, "activar miembros");
        return miembro.activar();
    }

    private void validarAutorizacion(MiembroLedger miembro, MiembroLedger solicitante, String accion) {
        Objects.requireNonNull(miembro, "miembro no puede ser nulo");
        Objects.requireNonNull(solicitante, "solicitante no puede ser nulo");
        
        if (!solicitante.esPropietario()) {
            throw new InvalidStateException("Solo un propietario puede " + accion);
        }
        
        if (!Objects.equals(miembro.getLedgerId(), solicitante.getLedgerId())) {
            throw new InvalidStateException("El propietario debe pertenecer al mismo ledger");
        }
    } 
}
