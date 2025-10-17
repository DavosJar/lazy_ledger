package com.lazyledger.backend.moduloLedger.objetivo.presentacion.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.identificadores.ObjetivoId;
import com.lazyledger.backend.moduloLedger.objetivo.dominio.Objetivo;
import com.lazyledger.backend.moduloLedger.objetivo.presentacion.dto.ObjetivoDto;
import com.lazyledger.backend.moduloLedger.objetivo.presentacion.dto.ObjetivoSaveRequest;

import jakarta.validation.ValidationException;

@Component
public class ObjetivoMapper {
    public ObjetivoDto toDto(Objetivo objetivo) {
        ObjetivoDto dto = new ObjetivoDto();
        dto.setId(objetivo.getId().toString());
        dto.setNombre(objetivo.getNombre());
        dto.setMontoObjetivo(String.valueOf(objetivo.getMontoObjetivo()));
        return dto;
    }

    public Objetivo toDomain(ObjetivoDto dto) {
        try {
            return Objetivo.create(
                ObjetivoId.of(UUID.fromString(dto.getId())),
                dto.getNombre(),
                dto.getMontoObjetivo() != null ? Double.parseDouble(dto.getMontoObjetivo()) : 0.0
            );
        } catch (Exception e) {
            throw new ValidationException("Datos inválidos en el DTO del objetivo: " + e.getMessage(), e);
        }
    }

    public Objetivo toDomain(ObjetivoSaveRequest request) {
        try {
            return Objetivo.create(
                ObjetivoId.of(UUID.randomUUID()),
                request.getNombre(),
                request.getMontoObjetivo() < 0 ? 0 : request.getMontoObjetivo()
            );
        } catch (Exception e) {
            throw new ValidationException("Datos inválidos en el request de guardado del objetivo: " + e.getMessage(), e);
        }
    }
}
