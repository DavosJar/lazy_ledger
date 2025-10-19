package com.lazyledger.backend.moduloLedger.transaccion.dominio.especificaciones;

import com.lazyledger.backend.commons.especificaciones.EspecificacionJpa;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Especificación para filtrar transacciones por rango de monto.
 * Permite buscar transacciones con montos entre un mínimo y un máximo.
 */
public class TransaccionPorRangoMonto implements EspecificacionJpa<TransaccionEntity> {
    
    private final Double montoMinimo;
    private final Double montoMaximo;

    public TransaccionPorRangoMonto(Double montoMinimo, Double montoMaximo) {
        this.montoMinimo = montoMinimo;
        this.montoMaximo = montoMaximo;
    }

    @Override
    public Predicate aPredicado(Root<TransaccionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (montoMinimo != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("monto"), montoMinimo));
        }
        
        if (montoMaximo != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("monto"), montoMaximo));
        }
        
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}