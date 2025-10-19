package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de uso para listar las invitaciones pendientes de un cliente.
 * 
 * Devuelve todos los miembros donde el cliente está inactivo (invitación pendiente).
 */
public class ListarInvitacionesPendientesUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;

    public ListarInvitacionesPendientesUseCase(MiembroLedgerRepository miembroLedgerRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
    }

    /**
     * Lista todas las invitaciones pendientes de un cliente.
     * 
     * @param clienteId ID del cliente
     * @return Lista de miembros inactivos (invitaciones pendientes)
     */
    public List<MiembroLedger> ejecutar(UUID clienteId) {
        return miembroLedgerRepository.findByClienteId(clienteId).stream()
            .filter(miembro -> !miembro.isActivo())  // Solo los inactivos (pendientes)
            .collect(Collectors.toList());
    }
}
