package com.lazyledger.backend.moduloLedger.transaccion.dominio.especificaciones;

import com.lazyledger.backend.commons.especificaciones.EspecificacionJpa;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Especificación para filtrar transacciones por intervalo de fechas.
 * Permite buscar transacciones entre una fecha de inicio y una fecha de fin.
 */
public class TransaccionPorIntervaloFechas implements EspecificacionJpa<TransaccionEntity> {
    
    private final LocalDateTime fechaDesde;
    private final LocalDateTime fechaHasta;

    public TransaccionPorIntervaloFechas(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    @Override
    public Predicate aPredicado(Root<TransaccionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (fechaDesde != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), fechaDesde));
        }
        
        if (fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), fechaHasta));
        }
        
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}