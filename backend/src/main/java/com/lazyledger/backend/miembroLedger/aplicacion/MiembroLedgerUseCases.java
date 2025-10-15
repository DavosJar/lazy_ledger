package com.lazyledger.backend.miembroLedger.aplicacion;

import com.lazyledger.backend.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.miembroLedger.dominio.MiembroLedger;


/**
 * Optional<MiembroLedger> save(MiembroLedger miembro);
    //ledgerId, clienteId -> debe ser unico, un cliente no puede tener mas de un rol en un ledger
    Optional<MiembroLedger> findByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId);
    List<MiembroLedger> findAll();
    boolean existsByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId);

    
 */
public class MiembroLedgerUseCases {
    private final MiembroLedgerRepository miembroLedgerRepository;

    public MiembroLedgerUseCases(MiembroLedgerRepository miembroLedgerRepository) {
        this.miembroLedgerRepository = miembroLedgerRepository;
    }

    public Optional<MiembroLedger> crearMiembroLeder(MiembroLedger miembroLedger) {
        return miembroLedgerRepository.save(miembroLedger);
    }

    public List<MiembroLedger> getAllMiembros() {
        return miembroLedgerRepository.findAll();
    }

    public Optional<MiembroLedger> getMiembroByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId) {
        return miembroLedgerRepository.findByClienteIdAndLedgerId(clienteId, ledgerId);
    }

    public boolean miembroExists(UUID clienteId, UUID ledgerId) {
        return miembroLedgerRepository.existsByClienteIdAndLedgerId(clienteId, ledgerId);
    }
    
    
    
}
