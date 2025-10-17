package com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto.MiembroLedgerDTO;
import com.lazyledger.backend.commons.exceptions.ValidationException;

@Component
public class MiembroLedgerMapper {

    public MiembroLedgerDTO toDTO(MiembroLedger miembro) {
        MiembroLedgerDTO dto = new MiembroLedgerDTO();
        dto.setClienteId(miembro.getClienteId().toString());
        dto.setLedgerId(miembro.getLedgerId().toString());
        dto.setRol(miembro.getRol().name());
        dto.setActivo(miembro.isActivo());
        return dto;
    }

    public MiembroLedger toDomain(MiembroLedgerDTO dto) {
        try {
            ClienteId clienteId = ClienteId.of(UUID.fromString(dto.getClienteId()));
            LedgerId ledgerId = LedgerId.of(UUID.fromString(dto.getLedgerId()));
            MiembroRol rol = MiembroRol.valueOf(dto.getRol());

            return MiembroLedger.rehydrate(clienteId, rol, ledgerId, dto.isActivo());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Datos inválidos en el DTO del miembro: " + e.getMessage(), e);
        }
    }
}