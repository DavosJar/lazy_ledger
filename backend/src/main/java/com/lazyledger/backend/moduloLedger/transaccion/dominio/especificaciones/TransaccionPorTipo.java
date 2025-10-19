package com.lazyledger.backend.moduloLedger.transaccion.dominio.especificaciones;

import com.lazyledger.backend.commons.especificaciones.EspecificacionJpa;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Especificación para filtrar transacciones por tipo.
 * Permite buscar transacciones de un tipo específico (INGRESO, GASTO, etc.).
 */
public class TransaccionPorTipo implements EspecificacionJpa<TransaccionEntity> {
    
    private final TipoTransaccion tipo;

    public TransaccionPorTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    @Override
    public Predicate aPredicado(Root<TransaccionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (tipo == null) {
            return cb.conjunction(); // Retorna un predicado verdadero (no filtra nada)
        }
        return cb.equal(root.get("tipo"), tipo);
    }
}