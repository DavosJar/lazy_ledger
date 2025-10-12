package com.lazyledger.backend.cliente.aplicacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.cliente.aplicacion.assembler.ClienteAssembler;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import java.util.UUID;
import com.lazyledger.backend.api.PagedResponse;
import org.springframework.hateoas.Link;

@Service
public class ClienteFacade {

    private final ClienteUseCases clienteUseCases;
    private final ClienteAssembler clienteAssembler;

    public ClienteFacade(ClienteRepository clienteRepository, ClienteAssembler clienteAssembler) {
        this.clienteUseCases = new ClienteUseCases(clienteRepository);
        this.clienteAssembler = clienteAssembler;
    }

    public ApiResponse<ClienteDTO> createCliente(ClienteSaveRequest clienteSaveRequest) {
        try {
            var cliente = clienteAssembler.toDomain(clienteSaveRequest);
            var createdCliente = clienteUseCases.createCliente(cliente);
            var dto = clienteAssembler.toDTO(createdCliente);
            return clienteAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de creación: " + e.getMessage(), e);
        }
    }

    public ApiResponse<ClienteDTO> getClienteById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var cliente = clienteUseCases.getClienteById(uuid);
            var dto = clienteAssembler.toDTO(cliente);
            return clienteAssembler.assemble(dto);
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
            var cliente = clienteAssembler.toDomain(clienteDTO);
            var updatedCliente = clienteUseCases.updateCliente(cliente);
            var dto = clienteAssembler.toDTO(updatedCliente);
            return clienteAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de actualización: " + e.getMessage(), e);
        }
    }

    public com.lazyledger.backend.api.PagedResponse<ClienteDTO> getAllClientes(int page, int size) {
        var allClientes = clienteUseCases.getAllClientes().stream()
                .map(clienteAssembler::toDTO)
                .collect(java.util.stream.Collectors.toList());
        long totalElements = allClientes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allClientes.size());
        var pageClientes = allClientes.subList(start, end);

        // For simplicity, return PagedResponse without links for now
        return new PagedResponse<>(pageClientes, page, size, totalElements, totalPages, new Link[0]);
    }

}
