package com.lazyledger.backend.moduloLedger.ledger.presentacion.mapper;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LazyDTO;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LazySaveRequest;
import com.lazyledger.backend.commons.enums.EstadoLedger;

@Component
public class LazyMapper {
    
    public LazyDTO toDto(Ledger ledger){
        LazyDTO dto = new LazyDTO();
        dto.setId(ledger.getId().toString());
        dto.setNombre(ledger.getNombre());
        dto.setDescripcion(ledger.getDescripcion());
        return dto;
    }

    public Ledger toDomain(LazyDTO dto){
        try {
            return Ledger.create(
                LedgerId.of(UUID.fromString(dto.getId())),
                dto.getNombre() != null ? dto.getNombre() : "",
                dto.getDescripcion() != null ? dto.getDescripcion() : "",
                EstadoLedger.ACTIVO
            );
        } catch (ValidationException e) {
            throw new ValidationException("Datos inválidos en el DTO del ledger: " + e.getMessage(), e);
        }
    }

    public Ledger toDomain(LazySaveRequest request){
        try {
            return Ledger.create(
                LedgerId.of(UUID.randomUUID()),
                request.getNombre() != null ? request.getNombre() : "",
                request.getDescripcion() != null ? request.getDescripcion() : "",
                EstadoLedger.ACTIVO
            );
        } catch (ValidationException e) {
            throw new ValidationException("Datos inválidos en la solicitud de guardar ledger: " + e.getMessage(), e);
        }

    }
}