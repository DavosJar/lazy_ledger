package com.lazyledger.backend.moduloLedger.transaccion.infraestructura.especificaciones;

import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.moduloLedger.transaccion.infraestructura.TransaccionEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * Especificación JPA para filtrar transacciones por categoría.
 */
public class TransaccionPorCategoriaSpec {
    
    public static Specification<TransaccionEntity> porCategoria(Categoria categoria) {
        return (root, query, cb) -> {
            if (categoria == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("categoria"), categoria);
        };
    }
}