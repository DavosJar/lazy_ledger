package com.lazyledger.backend.moduloLedger.transaccion.presentacion.mapper;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.TransaccionId;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Descripcion;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Fecha;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Monto;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Transaccion;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionDTO;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionSaveRequest;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;

@Component
public class TransaccionMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public TransaccionDTO toDTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setId(transaccion.getId().toString());
        dto.setLedgerId(transaccion.getLedgerId().toString());
        dto.setMonto(transaccion.getMonto() != null ? transaccion.getMonto().valor().doubleValue() : null);
        dto.setTipo(transaccion.getTipo() != null ? transaccion.getTipo().name() : null);
        dto.setCategoria(transaccion.getCategoria() != null ? transaccion.getCategoria().name() : null);
        dto.setDescripcion(transaccion.getDescripcion() != null ? transaccion.getDescripcion().valor() : null);
        dto.setFecha(transaccion.getFecha() != null ? transaccion.getFecha().valor().format(FORMATTER) : null);
        return dto;
    }

    public Transaccion toDomain(TransaccionDTO dto) {
        try {
            return Transaccion.create(
                    TransaccionId.of(UUID.fromString(dto.getId())),
                    LedgerId.of(UUID.fromString(dto.getLedgerId())),
                    Monto.of(BigDecimal.valueOf(dto.getMonto())),
                    dto.getTipo() != null ? TipoTransaccion.valueOf(dto.getTipo()) : null,
                    dto.getCategoria() != null ? Categoria.valueOf(dto.getCategoria()) : null,
                    dto.getDescripcion() != null ? Descripcion.of(dto.getDescripcion()) : null,
                    dto.getFecha() != null ? Fecha.of(LocalDateTime.parse(dto.getFecha(), FORMATTER)) : null
            );
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Datos inválidos en el DTO de la transacción: " + e.getMessage(), e);
        }
    }

    public Transaccion toDomain(TransaccionSaveRequest request) {
        try {
            return Transaccion.create(
                    TransaccionId.of(UUID.randomUUID()),
                    LedgerId.of(UUID.fromString(request.getLedgerId())),
                    Monto.of(BigDecimal.valueOf(request.getMonto())),
                    request.getTipo() != null ? TipoTransaccion.valueOf(request.getTipo()) : null,
                    request.getCategoria() != null ? Categoria.valueOf(request.getCategoria()) : null,
                    request.getDescripcion() != null ? Descripcion.of(request.getDescripcion()) : null,
                    request.getFecha() != null ? Fecha.of(LocalDateTime.parse(request.getFecha(), FORMATTER)) : null
            );
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Datos inválidos en la solicitud de guardar transacción: " + e.getMessage(), e);
        }
    }
}

