package com.lazyledger.backend.moduloLedger.miembroLedger.dominio;

import java.util.Objects;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.exceptions.InvalidStateException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;

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

    /**
     * Crea una invitación (miembro inactivo) para un cliente a un ledger, validando que
     * el solicitante sea propietario del mismo ledger.
     */
    public MiembroLedger invitar(MiembroLedger solicitante, ClienteId invitadoClienteId, LedgerId ledgerId) {
        Objects.requireNonNull(solicitante, "solicitante no puede ser nulo");
        Objects.requireNonNull(invitadoClienteId, "invitadoClienteId no puede ser nulo");
        Objects.requireNonNull(ledgerId, "ledgerId no puede ser nulo");

        if (!solicitante.esPropietario()) {
            throw new InvalidStateException("Solo un propietario puede invitar miembros");
        }
        if (!Objects.equals(solicitante.getLedgerId(), ledgerId)) {
            throw new InvalidStateException("El propietario debe pertenecer al mismo ledger");
        }

        // Miembro inactivo = invitación pendiente; rol por defecto: ASISTENTE
        return MiembroLedger.rehydrate(invitadoClienteId, MiembroRol.ASISTENTE, ledgerId, false);
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
