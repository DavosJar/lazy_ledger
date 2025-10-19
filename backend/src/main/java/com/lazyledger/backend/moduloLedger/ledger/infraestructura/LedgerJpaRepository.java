package com.lazyledger.backend.moduloLedger.ledger.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface LedgerJpaRepository extends JpaRepository<LedgerEntity, UUID>, JpaSpecificationExecutor<LedgerEntity> {
    boolean existsByNombre(String nombre);
}
