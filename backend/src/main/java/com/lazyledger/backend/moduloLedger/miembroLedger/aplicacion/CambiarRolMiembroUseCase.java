package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

public class CambiarRolMiembroUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;
    private final MiembroLedgerService miembroLedgerService;

    public CambiarRolMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                   MiembroLedgerService miembroLedgerService) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.miembroLedgerService = miembroLedgerService;
    }

    public MiembroLedger execute(UUID solicitanteId, UUID clienteId, UUID ledgerId, String nuevoRolStr) {
        // Validar rol
        MiembroRol nuevoRol;
        try {
            nuevoRol = MiembroRol.valueOf(nuevoRolStr);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Rol inválido: " + nuevoRolStr);
        }

        // Obtener solicitante y validar que sea propietario
        LedgerId ledgerIdObj = LedgerId.of(ledgerId);
        MiembroLedger solicitante = miembroLedgerRepository
            .findByClienteIdAndLedgerId(solicitanteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Solicitante no es miembro del ledger"));

        // Obtener miembro objetivo
        MiembroLedger miembroObjetivo = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Miembro no encontrado en el ledger"));

        // Cambiar rol usando el servicio
        MiembroLedger miembroActualizado = miembroLedgerService.cambiarRol(miembroObjetivo, solicitante, nuevoRol);

        // Guardar cambios
        return miembroLedgerRepository.save(miembroActualizado)
            .orElseThrow(() -> new ValidationException("Error al guardar los cambios"));
    }
}