package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

public class EliminarLedgerUseCase {

    private final MiembroLedgerRepository miembroLedgerRepository;
    private final MiembroLedgerService miembroLedgerService;

    public EliminarLedgerUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                MiembroLedgerService miembroLedgerService) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.miembroLedgerService = miembroLedgerService;
    }

    public void execute(UUID solicitanteId, UUID ledgerId) {
        // Obtener solicitante y validar que sea propietario
        MiembroLedger solicitante = miembroLedgerRepository
            .findByClienteIdAndLedgerId(solicitanteId, ledgerId)
            .orElseThrow(() -> new NotFoundException("Solicitante no es miembro del ledger"));

        if (!solicitante.esPropietario()) {
            throw new ValidationException("Solo el propietario puede eliminar el ledger");
        }

        // Obtener todos los miembros del ledger
        // Nota: Esto requeriría un método adicional en el repository para buscar por ledgerId
        // Por ahora, asumimos que se implementará este método

        // Desactivar todos los miembros (eliminación lógica)
        // Esto requeriría iterar sobre todos los miembros y desactivarlos

        // Nota: La eliminación completa del ledger debería manejarse en el módulo Ledger
        // Este UseCase solo maneja la parte de miembros
    }
}