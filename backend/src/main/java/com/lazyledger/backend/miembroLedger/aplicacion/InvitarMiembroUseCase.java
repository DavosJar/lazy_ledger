package com.lazyledger.backend.miembroLedger.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;

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
        ClienteId solicitanteClienteId = ClienteId.of(solicitanteId);
        LedgerId ledgerIdObj = LedgerId.of(ledgerId);

        MiembroLedger solicitante = miembroLedgerRepository
            .findByClienteIdAndLedgerId(solicitanteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Solicitante no es miembro del ledger"));

        if (!solicitante.esPropietario()) {
            throw new ValidationException("Solo el propietario puede invitar miembros");
        }

        // Validar que el cliente invitado exista y esté activo
        ClienteId invitadoClienteId = ClienteId.of(clienteId);
        boolean clienteExiste = clienteRepository.existsById(invitadoClienteId.value());
        if (!clienteExiste) {
            throw new ValidationException("El cliente invitado no existe");
        }

        // Verificar que el invitado no sea ya miembro
        if (miembroLedgerRepository.existsByClienteIdAndLedgerId(clienteId, ledgerId)) {
            throw new ValidationException("El cliente ya es miembro del ledger");
        }

        // Crear nuevo miembro con rol ASISTENTE
        MiembroLedger nuevoMiembro = MiembroLedger.create(invitadoClienteId, ledgerIdObj, MiembroRol.ASISTENTE);

        // Guardar en repositorio
        return miembroLedgerRepository.save(nuevoMiembro)
            .orElseThrow(() -> new ValidationException("Error al guardar el nuevo miembro"));
    }
}