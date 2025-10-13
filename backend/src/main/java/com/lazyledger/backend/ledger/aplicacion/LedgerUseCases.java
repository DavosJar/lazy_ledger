package com.lazyledger.backend.ledger.aplicacion;

import java.util.List;
import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.ledger.dominio.Ledger;
import com.lazyledger.backend.ledger.dominio.repositorio.LedgerRepository;

public class LedgerUseCases {

    private final LedgerRepository ledgerRepository;

    public LedgerUseCases(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    public Ledger createLedger(Ledger ledger) {
        try {
            return ledgerRepository.save(ledger);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar el ledger en la base de datos", e);       
        }
    }

    public Ledger getLedgerById(UUID id){
        try {
            return ledgerRepository.findById(id).orElseThrow(() -> new NotFoundException("Ledger no encontrado con ID: " + id));
        } catch (Exception e) {
            throw new InfrastructureException("Error al buscar el ledger por ID", e);
        }
    }

    public void deleteLedger(UUID id) {
        if (!ledgerRepository.existsById(id)) {
            throw new NotFoundException("Ledger no encontrado con ID: " + id);
        }
        try {
            ledgerRepository.delete(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar el ledger", e);
        }
    }

    public Ledger updateLedger(Ledger ledger) {
        if (!ledgerRepository.existsById(ledger.getId().value())) {
            throw new NotFoundException("Ledger no encontrado con ID: " + ledger.getId());
        }
        try {
            return ledgerRepository.save(ledger);
        } catch (Exception e) {
            throw new InfrastructureException("Error al actualizar el ledger en la base de datos", e);
        }
    }

    public List<Ledger> getAllLedgers() {
        try {
            return ledgerRepository.findAll();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener todos los ledgers", e);
        }
    }
}
