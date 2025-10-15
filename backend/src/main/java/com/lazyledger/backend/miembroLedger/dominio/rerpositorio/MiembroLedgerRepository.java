package com.lazyledger.backend.miembroLedger.dominio.rerpositorio;

import com.lazyledger.backend.miembroLedger.dominio.MiembroLedger;
import java.util.UUID;


import java.util.List;
import java.util.Optional;




public interface MiembroLedgerRepository {
    Optional<MiembroLedger> save(MiembroLedger miembro);
    //ledgerId, clienteId -> debe ser unico, un cliente no puede tener mas de un rol en un ledger
    Optional<MiembroLedger> findByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId);
    List<MiembroLedger> findAll();
    boolean existsByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId);
    List<MiembroLedger> findByClienteId(UUID clienteId);


}
