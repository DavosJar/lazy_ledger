package com.lazyledger.backend.moduloLedger.transaccion.dominio.especificaciones;

import com.lazyledger.backend.commons.especificaciones.EspecificacionJpa;
import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Especificación para filtrar transacciones por categoría.
 * Permite buscar transacciones de una categoría específica.
 */
public class TransaccionPorCategoria implements EspecificacionJpa<TransaccionEntity> {
    
    private final Categoria categoria;

    public TransaccionPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public Predicate aPredicado(Root<TransaccionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (categoria == null) {
            return cb.conjunction(); // Retorna un predicado verdadero (no filtra nada)
        }
        return cb.equal(root.get("categoria"), categoria);
    }
}