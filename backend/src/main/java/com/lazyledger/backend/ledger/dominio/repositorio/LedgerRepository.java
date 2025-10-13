package com.lazyledger.backend.ledger.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.ledger.dominio.Ledger;

public interface LedgerRepository {
    Ledger save(Ledger save);
    Optional<Ledger> findById(UUID id);
    List<Ledger> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByNombre(String nombre);
}
