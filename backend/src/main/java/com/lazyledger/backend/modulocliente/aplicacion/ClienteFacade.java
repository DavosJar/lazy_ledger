package com.lazyledger.backend.modulocliente.aplicacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.ResponseFactory;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import com.lazyledger.backend.modulocliente.aplicacion.assembler.ClienteHateoasLinkBuilder;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.modulocliente.presentacion.mapper.ClienteMapper;

import org.springframework.stereotype.Service;
import java.util.UUID;
import com.lazyledger.backend.api.PagedResponse;

@Service
public class ClienteFacade {

    private final ClienteUseCases clienteUseCases;
    private final ClienteMapper clienteMapper;
    private final ClienteHateoasLinkBuilder hateoasLinkBuilder;
    private final ResponseFactory responseFactory;

    public ClienteFacade(ClienteRepository clienteRepository, ClienteMapper clienteMapper, ClienteHateoasLinkBuilder hateoasLinkBuilder, ResponseFactory responseFactory) {
        this.clienteUseCases = new ClienteUseCases(clienteRepository);
        this.clienteMapper = clienteMapper;
        this.hateoasLinkBuilder = hateoasLinkBuilder;
        this.responseFactory = responseFactory;
    }

    public ApiResponse<ClienteDTO> createCliente(ClienteSaveRequest clienteSaveRequest) {
        try {
            var cliente = clienteUseCases.createCliente(
                clienteSaveRequest.getNombre(),
                clienteSaveRequest.getApellido(),
                clienteSaveRequest.getEmail(),
                clienteSaveRequest.getTipo(),
                clienteSaveRequest.getTelefono()
            );
            var dto = clienteMapper.toDTO(cliente.orElseThrow(() -> new ApplicationException("Error al crear el cliente")));
            var links = hateoasLinkBuilder.buildLinks(dto);
            return responseFactory.createResponse(dto, links);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de creación: " + e.getMessage(), e);
        }
    }

    public ApiResponse<ClienteDTO> getClienteById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var cliente = clienteUseCases.getClienteById(uuid)
                .orElseThrow(() -> new ApplicationException("Cliente no encontrado con ID: " + id));
            var dto = clienteMapper.toDTO(cliente);
            var links = hateoasLinkBuilder.buildLinks(dto);
            return responseFactory.createResponse(dto, links);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public void deleteCliente(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            clienteUseCases.deleteCliente(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public ApiResponse<ClienteDTO> updateCliente(ClienteDTO clienteDTO) {
        try {
            var cliente = clienteMapper.toDomain(clienteDTO);
            var updatedCliente = clienteUseCases.updateCliente(cliente);
            var dto = clienteMapper.toDTO(updatedCliente.orElseThrow(() -> new ApplicationException("Error al actualizar el cliente")));
            var links = hateoasLinkBuilder.buildLinks(dto);
            return responseFactory.createResponse(dto, links);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de actualización: " + e.getMessage(), e);
        }
    }

    public PagedResponse<ClienteDTO> getAllClientes(int page, int size) {
        var allClientes = clienteUseCases.getAllClientes().stream()
                .map(clienteMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
        long totalElements = allClientes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allClientes.size());
        var pageClientes = allClientes.subList(start, end);

        PagedResponse.PaginationInfo pagination = new PagedResponse.PaginationInfo(
            page, size, totalElements, totalPages, page == 0, page >= totalPages - 1
        );

        // Usar el builder HATEOAS para construir los enlaces
        var hateoasLinks = hateoasLinkBuilder.buildCollectionLinks(page, size, totalElements, totalPages);

        return responseFactory.createPagedResponse(pageClientes, pagination, hateoasLinks);
    }

}
