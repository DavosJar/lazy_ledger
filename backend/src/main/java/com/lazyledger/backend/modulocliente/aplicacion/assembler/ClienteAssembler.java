package com.lazyledger.backend.modulocliente.aplicacion.assembler;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.BaseAssembler;
import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.modulocliente.dominio.Cliente;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.modulocliente.presentacion.mapper.ClienteMapper;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClienteAssembler implements BaseAssembler<ClienteDTO> {

    private final ClienteMapper clienteMapper;
    private final ClienteHateoasLinkBuilder hateoasLinkBuilder;
    private final IdGenerator idGenerator;

    public ClienteAssembler(ClienteMapper clienteMapper, ClienteHateoasLinkBuilder hateoasLinkBuilder, IdGenerator idGenerator) {
        this.clienteMapper = clienteMapper;
        this.hateoasLinkBuilder = hateoasLinkBuilder;
        this.idGenerator = idGenerator;
    }

    @Override
    public ApiResponse<ClienteDTO> assemble(ClienteDTO dto) {
        Map<String, Link> links = hateoasLinkBuilder.buildLinks(dto);
        return new ApiResponse<ClienteDTO>(dto, links);
    }

    @Override
    public void addCommonLinks(ApiResponse<ClienteDTO> response) {
        // Los enlaces ya están incluidos en el assemble
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

    // Método para construir enlaces HATEOAS para colección
    public Map<String, Link> buildCollectionLinks(int page, int size, long totalElements, int totalPages) {
        return hateoasLinkBuilder.buildCollectionLinks(page, size, totalElements, totalPages);
    }
}