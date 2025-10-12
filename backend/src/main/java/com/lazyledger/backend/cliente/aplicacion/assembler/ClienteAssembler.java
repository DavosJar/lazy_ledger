package com.lazyledger.backend.cliente.aplicacion.assembler;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.BaseAssembler;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.cliente.presentacion.mapper.ClienteMapper;
import com.lazyledger.backend.cliente.dominio.Cliente;
import com.lazyledger.backend.commons.IdGenerator;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.lazyledger.backend.cliente.presentacion.ClienteController;

@Component
public class ClienteAssembler implements BaseAssembler<ClienteDTO> {

    private final ClienteMapper clienteMapper;
    private final IdGenerator idGenerator;

    public ClienteAssembler(ClienteMapper clienteMapper, IdGenerator idGenerator) {
        this.clienteMapper = clienteMapper;
        this.idGenerator = idGenerator;
    }

    @Override
    public ApiResponse<ClienteDTO> assemble(ClienteDTO dto) {
        Link selfLink = linkTo(methodOn(ClienteController.class).getClienteById(dto.getId())).withSelfRel();
        ApiResponse<ClienteDTO> response = new ApiResponse<>(dto, selfLink);
        addCommonLinks(response);
        return response;
    }

    @Override
    public void addCommonLinks(ApiResponse<ClienteDTO> response) {
        // Agregar enlaces comunes si es necesario, por ejemplo, enlace a lista de clientes
        Link allClientesLink = linkTo(methodOn(ClienteController.class).getAllClientes(0, 10)).withRel("all-clientes");
        response.add(allClientesLink);
    }

    // Método para convertir ClienteSaveRequest a Cliente (dominio)
    public Cliente toDomain(ClienteSaveRequest request) {
        // Usar idGenerator para asignar ID secuencial
        var cliente = clienteMapper.toDomain(request);
        // Reemplazar el ID generado con UUID.randomUUID() por el generado
        // Pero mapper ya usa UUID.randomUUID(), así que crear nuevo
        return Cliente.create(
                com.lazyledger.backend.commons.identificadores.ClienteId.of(idGenerator.nextId()),
                cliente.getNombreCompleto(),
                cliente.getEmail(),
                cliente.getTipo(),
                cliente.getTelefono()
        );
    }

    // Método para convertir ClienteDTO a Cliente (dominio)
    public Cliente toDomain(ClienteDTO dto) {
        return clienteMapper.toDomain(dto);
    }

    // Método para convertir Cliente (dominio) a ClienteDTO
    public ClienteDTO toDTO(Cliente cliente) {
        return clienteMapper.toDTO(cliente);
    }
}