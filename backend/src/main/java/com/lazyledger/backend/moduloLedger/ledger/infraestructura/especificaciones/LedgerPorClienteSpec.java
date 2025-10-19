package com.lazyledger.backend.moduloLedger.ledger.infraestructura.especificaciones;

import com.lazyledger.backend.moduloLedger.ledger.infraestructura.LedgerEntity;
import com.lazyledger.backend.moduloLedger.miembroLedger.infraestructura.MiembroLedgerEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Subquery;
import java.util.UUID;

public class LedgerPorClienteSpec {
    
    private LedgerPorClienteSpec() {}

    public static Specification<LedgerEntity> porCliente(UUID clienteId) {
        return (root, query, cb) -> {
            Subquery<UUID> sub = query.subquery(UUID.class);
            var m = sub.from(MiembroLedgerEntity.class);
            sub.select(m.get("id").get("ledgerId")).where(
                cb.and(
                    cb.equal(m.get("id").get("clienteId"), clienteId),
                    cb.equal(m.get("id").get("ledgerId"), root.get("id"))
                )
            );
            return cb.exists(sub);
        };
    }

    public static Specification<LedgerEntity> nombreContiene(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }
}
