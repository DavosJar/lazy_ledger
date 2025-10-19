package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;

public class InvitarMiembroUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;
    private final MiembroLedgerService miembroLedgerService;
    private final ClienteRepository clienteRepository;

    public InvitarMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                MiembroLedgerService miembroLedgerService,
                                ClienteRepository clienteRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.miembroLedgerService = miembroLedgerService;
        this.clienteRepository = clienteRepository;
    }

    public MiembroLedger execute(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        // Validar que el solicitante sea propietario
        LedgerId ledgerIdObj = LedgerId.of(ledgerId);

        MiembroLedger solicitante = miembroLedgerRepository
            .findByClienteIdAndLedgerId(solicitanteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Solicitante no es miembro del ledger"));

        // Autorización de propietario se valida nuevamente en el servicio de dominio

        // Validar que el cliente invitado exista y esté activo
        ClienteId invitadoClienteId = ClienteId.of(clienteId);
        boolean clienteExiste = clienteRepository.existsById(invitadoClienteId.value());
        if (!clienteExiste) {
            throw new ValidationException("El cliente invitado no existe");
        }

            // Verificar que el invitado no sea ya miembro activo
            var miembroExistente = miembroLedgerRepository.findByClienteIdAndLedgerId(clienteId, ledgerId);
            if (miembroExistente.isPresent()) {
                if (miembroExistente.get().isActivo()) {
                    throw new ValidationException("El cliente ya es miembro activo del ledger");
                } else {
                    // Si existe pero está inactivo (invitación pendiente), la reenviamos
                    throw new ValidationException("El cliente ya tiene una invitación pendiente");
                }
        }

            // Delegar la creación al servicio de dominio (miembro inactivo = invitación)
            MiembroLedger nuevoMiembro = miembroLedgerService.invitar(solicitante, invitadoClienteId, ledgerIdObj);

        // Guardar en repositorio
        return miembroLedgerRepository.save(nuevoMiembro)
            .orElseThrow(() -> new ValidationException("Error al guardar el nuevo miembro"));
    }
}