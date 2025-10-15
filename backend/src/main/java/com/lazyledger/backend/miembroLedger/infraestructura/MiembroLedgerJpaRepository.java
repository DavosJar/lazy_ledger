package com.lazyledger.backend.miembroLedger.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MiembroLedgerJpaRepository extends JpaRepository<MiembroLedgerEntity, MiembroLedgerId> {
    boolean existsByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId);

}
