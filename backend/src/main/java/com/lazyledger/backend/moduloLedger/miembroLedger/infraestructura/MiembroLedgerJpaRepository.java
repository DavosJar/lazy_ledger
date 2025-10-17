package com.lazyledger.backend.moduloLedger.miembroLedger.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MiembroLedgerJpaRepository extends JpaRepository<MiembroLedgerEntity, MiembroLedgerId> {
    boolean existsByIdClienteIdAndIdLedgerId(UUID clienteId, UUID ledgerId);
    List<MiembroLedgerEntity> findByIdClienteId(UUID clienteId);

}
