package com.lazyledger.backend.cliente.presentacion.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;
import com.lazyledger.backend.cliente.dominio.Cliente;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.cliente.dominio.Email;
import com.lazyledger.backend.cliente.dominio.NombreCompleto;
import com.lazyledger.backend.cliente.dominio.Telefono;
import com.lazyledger.backend.commons.identificadores.ClienteId;
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
        return Cliente.create(
                ClienteId.of(UUID.fromString(dto.getId())),
                NombreCompleto.of(dto.getNombre(), dto.getApellido()),
                Email.of(dto.getEmail()),
                dto.getTipo() != null ? TipoCliente.valueOf(dto.getTipo()) : null,
                dto.getTelefono() != null ? Telefono.of(dto.getTelefono()) : null
        );
    }

    public Cliente toDomain(ClienteSaveRequest request) {
        return Cliente.create(
                ClienteId.of(UUID.randomUUID()),
                NombreCompleto.of(request.getNombre(), request.getApellido()),
                Email.of(request.getEmail()),
                request.getTipo() != null ? TipoCliente.valueOf(request.getTipo()) : null,
                request.getTelefono() != null ? Telefono.of(request.getTelefono()) : null
        );
    }
}
