package com.lazyledger.backend.moduloLedger.transaccion.dominio.especificaciones;

import com.lazyledger.backend.commons.especificaciones.EspecificacionJpa;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.UUID;

/**
 * Especificación para filtrar transacciones por ledger.
 * Permite buscar todas las transacciones asociadas a un ledger específico.
 */
public class TransaccionPorLedger implements EspecificacionJpa<TransaccionEntity> {
    
    private final UUID ledgerId;

    public TransaccionPorLedger(UUID ledgerId) {
        this.ledgerId = ledgerId;
    }

    @Override
    public Predicate aPredicado(Root<TransaccionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (ledgerId == null) {
            return cb.conjunction(); 
        }
        return cb.equal(root.get("ledgerId"), ledgerId);
    }
}