package com.lazyledger.backend.moduloLedger.transaccion.infraestructura.especificaciones;

import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Especificación JPA para filtrar transacciones por intervalo de fechas.
 */
public class TransaccionPorIntervaloFechasSpec {
    
    public static Specification<TransaccionEntity> porIntervaloFechas(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (fechaDesde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), fechaDesde));
            }
            
            if (fechaHasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), fechaHasta));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}