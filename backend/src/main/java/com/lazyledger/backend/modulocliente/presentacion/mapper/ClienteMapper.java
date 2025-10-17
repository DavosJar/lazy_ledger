package com.lazyledger.backend.modulocliente.presentacion.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.modulocliente.dominio.Cliente;
import com.lazyledger.backend.modulocliente.dominio.Email;
import com.lazyledger.backend.modulocliente.dominio.NombreCompleto;
import com.lazyledger.backend.modulocliente.dominio.Telefono;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.commons.enums.TipoCliente;



@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId().toString());
        dto.setNombre(cliente.getNombreCompleto().getNombre());
        dto.setApellido(cliente.getNombreCompleto().getApellido());
        dto.setEmail(cliente.getEmail().toString());
        dto.setTipo(cliente.getTipo() != null ? cliente.getTipo().name() : null);
        dto.setTelefono(cliente.getTelefono() != null ? cliente.getTelefono().toString() : null);
        return dto;
    }

    public Cliente toDomain(ClienteDTO dto) {
        try {
            return Cliente.create(
                    ClienteId.of(UUID.fromString(dto.getId())),
                    NombreCompleto.of(dto.getNombre(), dto.getApellido()),
                    Email.of(dto.getEmail()),
                    dto.getTipo() != null ? TipoCliente.valueOf(dto.getTipo()) : null,
                    dto.getTelefono() != null ? Telefono.of(dto.getTelefono()) : null
            );
        } catch (ValidationException e) {
            throw new ValidationException("Datos inválidos en el DTO del cliente: " + e.getMessage(), e);
        }
    }
    /**
     * Convertir ClienteSaveRequest a Cliente (dominio)
     * 
     * @param request
     * @return
     */
    public Cliente toDomain(ClienteSaveRequest request) {
        try {
            return Cliente.create(
                    ClienteId.of(UUID.randomUUID()),
                    NombreCompleto.of(request.getNombre(), request.getApellido()),
                    Email.of(request.getEmail()),
                    request.getTipo() != null ? TipoCliente.valueOf(request.getTipo()) : null,
                    request.getTelefono() != null ? Telefono.of(request.getTelefono()) : null
            );
        } catch (ValidationException e) {
            throw new ValidationException("Datos inválidos en la solicitud de guardar cliente: " + e.getMessage(), e);
        }
    }
}
