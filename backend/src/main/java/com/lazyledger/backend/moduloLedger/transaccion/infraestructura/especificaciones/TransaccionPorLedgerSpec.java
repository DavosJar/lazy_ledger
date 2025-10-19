package com.lazyledger.backend.moduloLedger.transaccion.infraestructura.especificaciones;

import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Especificación JPA para filtrar transacciones por ledger.
 */
public class TransaccionPorLedgerSpec {
    
    public static Specification<TransaccionEntity> porLedger(UUID ledgerId) {
        return (root, query, cb) -> {
            if (ledgerId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("ledgerId"), ledgerId);
        };
    }
}