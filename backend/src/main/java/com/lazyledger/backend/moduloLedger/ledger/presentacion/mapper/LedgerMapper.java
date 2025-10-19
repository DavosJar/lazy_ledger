package com.lazyledger.backend.moduloLedger.ledger.presentacion.mapper;

import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades Ledger y DTOs (mapeador puro).
 */
@Component
public class LedgerMapper {

    /**
     * Convierte una entidad Ledger de dominio a DTO.
     */
    public LedgerDTO toDTO(Ledger ledger) {
        if (ledger == null) {
            return null;
        }
        return new LedgerDTO(
            ledger.getId().value().toString(),
            ledger.getNombre(),
            ledger.getDescripcion(),
            ledger.getEstado()
        );
    }
}
