package com.lazyledger.backend.moduloLedger.deuda.presentacion.mapper;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.enums.EstadoDeuda;
import com.lazyledger.backend.commons.identificadores.DeudaId;
import com.lazyledger.backend.moduloLedger.deuda.dominio.Deuda;
import com.lazyledger.backend.moduloLedger.deuda.dominio.FechasDeuda;
import com.lazyledger.backend.moduloLedger.deuda.presentacion.dto.DeudaDTO;
import com.lazyledger.backend.moduloLedger.deuda.presentacion.dto.DeudaSaveRequest;

import jakarta.validation.ValidationException;

@Component
public class DeudaMapper {
    
    public DeudaDTO toDTO(Deuda deuda){
        DeudaDTO dto = new DeudaDTO();
        dto.setId(deuda.getId().toString());
        dto.setNombre(deuda.getNombre());
        dto.setMontoTotal(deuda.getMontoTotal());
        dto.setDescripcion(deuda.getDescripcion());
        dto.setFechaCreacion(deuda.getFechaCreacion().toString());
        dto.setFechaVencimiento(deuda.getFechaVencimiento().toString());
        dto.setEstado(deuda.getEstado().name());
        return dto;
    }

    public Deuda toDomain(DeudaDTO dto){
        try {
            return Deuda.create(
                DeudaId.of(UUID.fromString(dto.getId())),
                dto.getNombre(),
                dto.getMontoTotal(),
                dto.getDescripcion(),
                FechasDeuda.of(LocalDate.parse(dto.getFechaCreacion()), LocalDate.parse(dto.getFechaVencimiento())),
                dto.getEstado() != null ? EstadoDeuda.valueOf(dto.getEstado()) : null
            );
        } catch (Exception e) {
            throw new ValidationException("Datos inválidos en el DTO de la deuda: " + e.getMessage(), e);
        }
    }

    public Deuda toDomain(DeudaSaveRequest request){
        try {
            return Deuda.create(
                DeudaId.of(UUID.randomUUID()),
                request.getNombre(),
                request.getMontoTotal(),
                request.getDescripcion(),
                FechasDeuda.of(LocalDate.now(), LocalDate.parse(request.getFechaVencimiento())),
                EstadoDeuda.PENDIENTE
            );
        } catch (Exception e) {
            throw new ValidationException("Datos inválidos en la solicitud de creación de deuda: " + e.getMessage(), e);
        }
    }
    
}
