package com.lazyledger.backend.moduloLedger.transaccion.infraestructura.especificaciones;

import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * Especificación JPA para filtrar transacciones por tipo.
 */
public class TransaccionPorTipoSpec {
    
    public static Specification<TransaccionEntity> porTipo(TipoTransaccion tipo) {
        return (root, query, cb) -> {
            if (tipo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipo"), tipo);
        };
    }
}