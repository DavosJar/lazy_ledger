package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

import java.util.UUID;

/**
 * Caso de uso para rechazar una invitación a un ledger.
 * 
 * Elimina el miembro inactivo (invitación pendiente).
 */
public class RechazarInvitacionUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;

    public RechazarInvitacionUseCase(MiembroLedgerRepository miembroLedgerRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
    }

    /**
     * Rechaza una invitación pendiente.
     * 
     * @param clienteId ID del cliente que rechaza
     * @param ledgerId ID del ledger al que fue invitado
     */
    public void ejecutar(UUID clienteId, UUID ledgerId) {
        // Buscar el miembro
        MiembroLedger miembro = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("No existe invitación para este ledger"));

        // Validar que sea una invitación pendiente (inactivo)
        if (miembro.isActivo()) {
            throw new ValidationException("No puedes rechazar porque ya eres miembro activo");
        }

        // Eliminar el miembro (rechazar invitación)
        // TODO: En el futuro, podríamos marcar como RECHAZADO y eliminar con scheduler después de 7 días
        miembroLedgerRepository.delete(clienteId, ledgerId);
    }
}
