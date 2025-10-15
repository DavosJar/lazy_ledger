package com.lazyledger.backend.miembroLedger.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;

public class ExpulsarMiembroUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;
    private final MiembroLedgerService miembroLedgerService;

    public ExpulsarMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                 MiembroLedgerService miembroLedgerService) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.miembroLedgerService = miembroLedgerService;
    }

    public void execute(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        // Obtener solicitante y validar que sea propietario
        MiembroLedger solicitante = miembroLedgerRepository
            .findByClienteIdAndLedgerId(solicitanteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Solicitante no es miembro del ledger"));

        // Obtener miembro objetivo
        MiembroLedger miembroObjetivo = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Miembro no encontrado en el ledger"));

        // Expulsar usando el servicio (desactivar)
        MiembroLedger miembroDesactivado = miembroLedgerService.desactivar(miembroObjetivo, solicitante);

        // Guardar cambios
        miembroLedgerRepository.save(miembroDesactivado)
            .orElseThrow(() -> new ValidationException("Error al guardar los cambios"));
    }
}