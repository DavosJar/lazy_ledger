package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

import java.util.UUID;

/**
 * Caso de uso para aceptar una invitación a un ledger.
 * 
 * Cambia el estado del miembro de inactivo (invitación pendiente) a activo.
 */
public class AceptarInvitacionUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;

    public AceptarInvitacionUseCase(MiembroLedgerRepository miembroLedgerRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
    }

    /**
     * Acepta una invitación pendiente.
     * 
     * @param clienteId ID del cliente que acepta
     * @param ledgerId ID del ledger al que fue invitado
     * @return El miembro ahora activo
     */
    public MiembroLedger ejecutar(UUID clienteId, UUID ledgerId) {
        // Buscar el miembro
        MiembroLedger miembro = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("No existe invitación para este ledger"));

        // Validar que sea una invitación pendiente (inactivo)
        if (miembro.isActivo()) {
            throw new ValidationException("Ya eres miembro activo de este ledger");
        }

        // Activar el miembro (aceptar invitación)
        MiembroLedger miembroActivo = miembro.activar();

        // Guardar el cambio
        return miembroLedgerRepository.save(miembroActivo)
            .orElseThrow(() -> new ValidationException("Error al aceptar la invitación"));
    }
}
