package com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LedgerRepository {
    Ledger save(Ledger save);
    Optional<Ledger> findById(UUID id);
    List<Ledger> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByNombre(String nombre);
    Page<Ledger> buscarPorClienteConFiltros(UUID clienteId, String nombre, Pageable pageable);
}
