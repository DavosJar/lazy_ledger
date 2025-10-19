package com.lazyledger.backend.moduloLedger.ledger.aplicacion;

import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de uso: Listar todos los ledgers donde un cliente es miembro.
 */
@Service
public class BuscarLedgersDeClienteUseCase {
    private final MiembroLedgerRepository miembroLedgerRepository;
    private final LedgerRepository ledgerRepository;

    public BuscarLedgersDeClienteUseCase(MiembroLedgerRepository miembroLedgerRepository, LedgerRepository ledgerRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public List<Ledger> ejecutar(ClienteId clienteId) {
        // Buscar todos los miembros de ese cliente
        List<MiembroLedger> membresias = miembroLedgerRepository.findByClienteId(clienteId.value());
        Set<UUID> ledgerIds = membresias.stream()
                .map(m -> m.getLedgerId().value())
                .collect(Collectors.toSet());
        // Buscar todos los ledgers por esos IDs
        return ledgerRepository.findAll().stream()
                .filter(l -> ledgerIds.contains(l.getId().value()))
                .collect(Collectors.toList());
    }
}
