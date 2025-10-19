package com.lazyledger.backend.moduloLedger.transaccion.infraestructura.especificaciones;

import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Especificación JPA para filtrar transacciones por rango de monto.
 */
public class TransaccionPorRangoMontoSpec {
    
    public static Specification<TransaccionEntity> porRangoMonto(Double montoMinimo, Double montoMaximo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (montoMinimo != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("monto"), montoMinimo));
            }
            
            if (montoMaximo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("monto"), montoMaximo));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}